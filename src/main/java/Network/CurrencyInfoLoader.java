package Network;

import Utils.DateHelper;
import models.CurrencyModel;
import models.RateModel;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

public class CurrencyInfoLoader {
	private static final String baseUrl = "http://api.nbp.pl/api/exchangerates/rates/";
	private static final int DAYS_LIMIT = 93;
	private ObjectMapper objectMapper;

	private static CurrencyInfoLoader instance = null;

	private CurrencyInfoLoader() {
		this.objectMapper = new ObjectMapper();

	}

	public static CurrencyInfoLoader getInstance() {
		if (instance == null)
			instance = new CurrencyInfoLoader();
		return instance;
	}

	/** Returns data for N last days */
	public CurrencyModel getCurrencyModelForLastDays(String tableType, String currency, long days) {
		try {
			return objectMapper.readValue(buildURL(tableType, currency, String.valueOf(days)), CurrencyModel.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/** Returns data for wanted Period
	 *  Period creating: Period period = Period.of(years, months, days);
	 *  Counts only working days.
	 *  Example: period for 1 month will contain ~20 records in CurrencyModel.rates
	 * */

	public CurrencyModel getCurrencyModelForPeriod(String tableType, String currency, Period period) {

		LocalDate today = LocalDate.now();
		LocalDate then = today.minus(period);

		CurrencyModel lastModel = null;
		List<RateModel> modelList = new ArrayList<>();

		long daysBetween = then.until(today, ChronoUnit.DAYS);
		LocalDate startDate = then;
		LocalDate endDate = then.plusDays(DAYS_LIMIT);
		if (endDate.isAfter(today))
			endDate = today;

		do {
			try {
				URL url = buildURL(tableType, currency, startDate.toString(), endDate.toString());
				lastModel = objectMapper.readValue(url, CurrencyModel.class);
			} catch (Exception ex) {
				try {
					URL url = buildURL("b", currency, startDate.toString(), endDate.toString());
					lastModel = (CurrencyModel) objectMapper.readValue(url, CurrencyModel.class);
				} catch (Exception ex2){
					ex2.printStackTrace();
				}
			}
			modelList.addAll(lastModel.getRates());
			startDate = endDate.plusDays(1);
			endDate = endDate.plusDays(DAYS_LIMIT);
			if (endDate.isAfter(today))
				endDate = today;
		} while ((daysBetween -= DAYS_LIMIT) > 0);

		if (lastModel != null) {
			lastModel.setRates(modelList);
			return lastModel;
		}
		return null;
	}

	private URL buildURL(String tableType, String currencySign, String last) throws MalformedURLException {
		String url = baseUrl;
		url += tableType + "/";
		url += currencySign + "/";
		url += "/last/" + last + "/";
		url += "?format=json";
		return new URL(url);
	}

	private URL buildURL(String tableType, String currencySign, String startDate, String endDate) throws MalformedURLException {
		String url = baseUrl;
		url += tableType + "/";
		url += currencySign + "/";
		url += startDate + "/";
		url += endDate + "/";
		url += "?format=json";
		System.out.println(url);
		return new URL(url);
	}
}
