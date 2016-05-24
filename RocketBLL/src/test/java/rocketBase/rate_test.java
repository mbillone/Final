package rocketBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class rate_test {

	//TODO - RocketBLL rate_test
	//		Check to see if a known credit score returns a known interest rate
	
	//TODO - RocketBLL rate_test
	//		Check to see if a RateException is thrown if there are no rates for a given
	//		credit score
	@Test
	public void KnownGetTest() throws Exception{
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		int creditScore = 800;
		double rate = 0;
		double payment = 0;
		try{
			rate = RateBLL.getRate(creditScore) / (100*12);
			System.out.println(rate);
		} catch (RateException e){
			throw e;
		}
		
		payment = rocketBase.RateBLL.getPayment(rate, 200, 200000.0, 0.0, false);
		System.out.println(Math.round(payment*100.00) / 100.00);
		assertTrue(Math.round(payment*100.00) / 100.00 == 1321.28);
	}
	@Test
	public void ExceptionTest() throws Exception{
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		int creditScore = 100;
		try{
			double rate = RateBLL.getRate(creditScore);
			System.out.println(rate);
		}
		catch (RateException e){
			throw e;
		}
		
	}

}
