package controllers;

import models.CountSessionStateModel;
import models.CurrencyModel;
import models.RateModel;
import org.junit.Before;
import org.junit.Test;
import Network.CurrencyInfoLoader;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

//TODO: remove and replace with tests for CurrencyInfoLoader
public class CurrencyInfoControllerTest {

	private CurrencyModel currentPeriod;

	@Before
	public void setUp() {
	}

	@Test
	public void testForCurrencyInfoLoaderBetaVersionUsd() {
		Period period = Period.of(1, 0, 0);

		LocalDate today = LocalDate.now();
		LocalDate then = today.minus(period);
		//long daysBetween = then.until(today, ChronoUnit.DAYS);
		//System.out.println(WorkDaysBetween(then, today));
		String url = "http://api.nbp.pl/api/exchangerates/rates/a/usd/";
		url += then.toString() + "/";
		url += then.plusDays(93).toString();
		CurrencyModel model =  CurrencyInfoController.createCurrencyModelFromUrl(url);
		for(RateModel mod: model.getRates()) {
			System.out.println(mod.getNo());
		}
		System.out.println(model.getRates().size());
		System.out.println(then.toString());
		CurrencyModel model1 = CurrencyInfoLoader.getInstance().getCurrencyModelForPeriod("a", "usd", period);
		System.out.println(model1.getRates().size());
	}

	@Test
	public void testForCurrencyInfoLoaderBetaVersionEur() {
		Period period = Period.of(1, 0, 0);

		LocalDate today = LocalDate.now();
		LocalDate then = today.minus(period);
		String url = "http://api.nbp.pl/api/exchangerates/rates/a/eur/";
		url += then.toString() + "/";
		url += then.plusDays(93).toString();
		CurrencyModel model =  CurrencyInfoController.createCurrencyModelFromUrl(url);
		for(RateModel mod: model.getRates()) {
			System.out.println(mod.getNo());
		}
		System.out.println(model.getRates().size());
		System.out.println(then.toString());
		CurrencyModel model1 = CurrencyInfoLoader.getInstance().getCurrencyModelForPeriod("a", "eur", period);
		System.out.println(model1.getRates().size());
	}
	@Test
	public void testForCurrencyInfoLoaderBetaVersionUAH() {
		Period period = Period.of(1, 0, 0);

		LocalDate today = LocalDate.now();
		LocalDate then = today.minus(period);
		String url = "http://api.nbp.pl/api/exchangerates/rates/a/uah/";
		url += then.toString() + "/";
		url += then.plusDays(93).toString();
		CurrencyModel model =  CurrencyInfoController.createCurrencyModelFromUrl(url);
		for(RateModel mod: model.getRates()) {
			System.out.println(mod.getNo());
		}
		System.out.println(model.getRates().size());
		System.out.println(then.toString());
		CurrencyModel model1 = CurrencyInfoLoader.getInstance().getCurrencyModelForPeriod("a", "uah", period);
		System.out.println(model1.getRates().size());
	}

//	@Test
//	public void testForCurrencyInfoLoaderBetaVersionEUR() {
//
//		String expecteddata = "4.3084";
//		String url = "http://api.nbp.pl/api/exchangerates/rates/a/eur/2017-02-02/";
//		//LocalDate today = LocalDate.now();
//		//LocalDate then = today.minus(period);
//
//
//		CurrencyModel model =  CurrencyInfoController.createCurrencyModelFromUrl(url);
//		for(RateModel mod: model.getRates()) {
//			System.out.println(mod.getNo());
//		}
//		System.out.println(model.getRates().size());
//
//		assertEquals(expecteddata, url,false);
//	}
	@Test
	public void emptyPeriodShouldReturnZeroedStateModel() {

		currentPeriod = new CurrencyModel("a","euro", "eur", new ArrayList<>());
		CountSessionStateModel resultState = CurrencyInfoController.countSessionStateForThePeriod(currentPeriod);

		assertThat(resultState.getFellResult(), is(0));
		assertThat(resultState.getGrownResult(), is(0));
		assertThat(resultState.getMaintainedResult(), is(0));
	}

	@Test
	public void weeklyGrowingSessionShouldReturnResultWithFourGrowthsNoticed() {
		List<RateModel> weekSession = new ArrayList<>();
		weekSession.add(new RateModel("some", "some", 1.0,1.0,1.0));
		weekSession.add(new RateModel("some", "some", 2.0, 2.0, 2.0));
		weekSession.add(new RateModel("some", "some", 3.0, 3.0, 3.0));
		weekSession.add(new RateModel("some", "some", 4.0, 4.0, 4.0));
		weekSession.add(new RateModel("some", "some", 5.0, 5.0, 5.0));

		currentPeriod = new CurrencyModel("a", "euro", "eur", weekSession);
		CountSessionStateModel resultState = CurrencyInfoController.countSessionStateForThePeriod(currentPeriod);

		assertThat(resultState.getGrownResult(), is(4));
		assertThat(resultState.getMaintainedResult(), is(0));
		assertThat(resultState.getFellResult(), is(0));
	}

	@Test
	public void weeklyStableSessionShouldReturnResultWithFourMaintainingsNoticed() {
		List<RateModel> weekSession = new ArrayList<>();
		weekSession.add(new RateModel("some", "some", 1.004,2.004,3.004));
		weekSession.add(new RateModel("some", "some", 1.002,2.002,3.002));
		weekSession.add(new RateModel("some", "some", 1.001,2.001,3.001));
		weekSession.add(new RateModel("some", "some", 1.003,2.003,3.003));
		weekSession.add(new RateModel("some", "some", 1.0,2.0,3.0));

		currentPeriod = new CurrencyModel("a", "euro", "eur", weekSession);
		CountSessionStateModel resultState = CurrencyInfoController.countSessionStateForThePeriod(currentPeriod);

		assertThat(resultState.getGrownResult(), is(0));
		assertThat(resultState.getMaintainedResult(), is(4));
		assertThat(resultState.getFellResult(), is(0));
	}

	@Test
	public void weeklyFallingSessionShouldReturnWithFourFellsNoticed() {
		List<RateModel> weekSession = new ArrayList<>();
		weekSession.add(new RateModel("some", "some", 5.0,5.0,7.0));
		weekSession.add(new RateModel("some", "some", 4.0,5.0,7.0));
		weekSession.add(new RateModel("some", "some", 3.0,5.0,7.0));
		weekSession.add(new RateModel("some", "some", 2.0,5.0,7.0));
		weekSession.add(new RateModel("some", "some", 1.0,5.0,7.0));

		currentPeriod = new CurrencyModel("a", "euro", "eur", weekSession);
		CountSessionStateModel resultState = CurrencyInfoController.countSessionStateForThePeriod(currentPeriod);

		assertThat(resultState.getGrownResult(), is(0));
		assertThat(resultState.getMaintainedResult(), is(0));
		assertThat(resultState.getFellResult(), is(4));
	}
}