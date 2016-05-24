package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import rocketDomain.RateDomainModel;
import exceptions.RateException;
import rocketData.LoanRequest;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) throws RateException
	{
		//TODO - RocketBLL RateBLL.getRate - make sure you throw any exception
		
		//		Call RateDAL.getAllRates... this returns an array of rates
		//		write the code that will search the rates to determine the 
		//		interest rate for the given credit score
		//		hints:  you have to sort the rates...  you can do this by using
		//			a comparator... or by using an OrderBy statement in the HQL
		ArrayList<RateDomainModel> rates = _RateDAL.getAllRates();
		RateDomainModel someRate = null;
		double dRate = -1;
		for (RateDomainModel rate : rates){
			if (rate.getiMinCreditScore() == GivenCreditScore){
				dRate = rate.getdInterestRate();
				someRate = rate;
			}
		}
		
		//TODO - RocketBLL RateBLL.getRate
		//			obviously this should be changed to return the determined rate
		if (someRate == null){
			throw new RateException(someRate);
		}
		else{
		return dRate;
		}
		
	}
	
	
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		return -FinanceLib.pmt(r, n, p, f, t);
	}
	
	public boolean IncomeCheck(LoanRequest lq){
		
		boolean Checked = false;
		if ((((lq.getIncome()/12) * .28) > lq.getdPayment()) && ((((lq.getIncome()/12) - lq.getExpenses()) * .36) > lq.getdPayment())){
			Checked = true;
		}
		return Checked;	
	}
	
}
