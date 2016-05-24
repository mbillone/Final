package rocket.app.view;

import java.text.NumberFormat;

import org.springframework.format.number.CurrencyFormatter;


import eNums.eAction;
import exceptions.RateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketBase.RateDAL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)
	
	@FXML
	private TextField txtIncome;
	
	@FXML
	private TextField txtExpenses;

	@FXML
	private TextField txtCreditScore;
	
	@FXML
	private TextField txtHouseCost;
	
	@FXML
	private TextField txtDownPayment;

	// list for combo box
	ObservableList<String> termList = FXCollections.observableArrayList("15 Years", "30 Years") ;
	
	@FXML
	private ComboBox comboTerm;

	@FXML
	private TextField txtMortgagePayment;

	@FXML
	private TextField txtRate;
	
	@FXML
	private Button btnCalcPayment;
	
	
	@FXML
	private void initialize(){
		
		comboTerm.setItems(termList);
		comboTerm.setValue(termList.get(1));
	}
	

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq
		
		lq.setdAmount(Integer.parseInt(txtHouseCost.getText()) - Double.parseDouble(txtDownPayment.getText()));
		lq.setIncome( Integer.parseInt(txtIncome.getText()));
		lq.setExpenses(Integer.parseInt(txtExpenses.getText()));
		lq.setiCreditScore(Integer.parseInt(txtCreditScore.getText()));
		
		try{
			lq.setdRate(RateBLL.getRate(Integer.parseInt(txtCreditScore.getText())));
		}
		catch (RateException e){
			lq.setdRate(-1.0);
		}
		if (comboTerm.getValue() == "30 years"){
			lq.setiTerm(30);
		}
		else{
			lq.setiTerm(15);
		}

		a.setLoanRequest(lq);
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		
		RateBLL _RateBLL = new RateBLL();
		
		if (lRequest.getdRate() == -1.0){
			
			txtRate.setText("Your credit score does not qualify for a loan");
			txtMortgagePayment.setText("Your credit score does not qualify for a loan");
			
		}
		else if (_RateBLL.IncomeCheck(lRequest) == false){
			
			txtRate.setText("Price for house is too high to qualify for a loan");
			txtMortgagePayment.setText("Price for house is too high to qualify for a loan");
			
		}
		else {
			txtMortgagePayment.setText(Double.toString(lRequest.getdPayment()));
			txtRate.setText(Double.toString(lRequest.getdRate() / 100));
		}
		
	}
}
