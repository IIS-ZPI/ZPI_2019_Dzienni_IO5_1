package controllers;

import models.CountSessionStateModel;
import models.CurrencyModel;
import models.RateModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CurrencyInfoControllerTest {

	private CurrencyModel currentPeriod;

	@Before
	public void setUp() {
	}

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
		weekSession.add(new RateModel("some", "some", 1.0));
		weekSession.add(new RateModel("some", "some", 2.0));
		weekSession.add(new RateModel("some", "some", 3.0));
		weekSession.add(new RateModel("some", "some", 4.0));
		weekSession.add(new RateModel("some", "some", 5.0));

		currentPeriod = new CurrencyModel("a", "euro", "eur", weekSession);
		CountSessionStateModel resultState = CurrencyInfoController.countSessionStateForThePeriod(currentPeriod);

		assertThat(resultState.getGrownResult(), is(4));
		assertThat(resultState.getMaintainedResult(), is(0));
		assertThat(resultState.getFellResult(), is(0));
	}

	@Test
	public void weeklyStableSessionShouldReturnResultWithFourMaintainingsNoticed() {
		List<RateModel> weekSession = new ArrayList<>();
		weekSession.add(new RateModel("some", "some", 1.004));
		weekSession.add(new RateModel("some", "some", 1.002));
		weekSession.add(new RateModel("some", "some", 1.001));
		weekSession.add(new RateModel("some", "some", 1.003));
		weekSession.add(new RateModel("some", "some", 1.0));

		currentPeriod = new CurrencyModel("a", "euro", "eur", weekSession);
		CountSessionStateModel resultState = CurrencyInfoController.countSessionStateForThePeriod(currentPeriod);

		assertThat(resultState.getGrownResult(), is(0));
		assertThat(resultState.getMaintainedResult(), is(4));
		assertThat(resultState.getFellResult(), is(0));
	}

	@Test
	public void weeklyFallingSessionShouldReturnWithFourFellsNoticed() {
		List<RateModel> weekSession = new ArrayList<>();
		weekSession.add(new RateModel("some", "some", 5.0));
		weekSession.add(new RateModel("some", "some", 4.0));
		weekSession.add(new RateModel("some", "some", 3.0));
		weekSession.add(new RateModel("some", "some", 2.0));
		weekSession.add(new RateModel("some", "some", 1.0));

		currentPeriod = new CurrencyModel("a", "euro", "eur", weekSession);
		CountSessionStateModel resultState = CurrencyInfoController.countSessionStateForThePeriod(currentPeriod);

		assertThat(resultState.getGrownResult(), is(0));
		assertThat(resultState.getMaintainedResult(), is(0));
		assertThat(resultState.getFellResult(), is(4));
	}
}