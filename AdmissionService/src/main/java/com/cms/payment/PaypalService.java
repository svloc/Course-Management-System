package com.cms.payment;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public class PaypalService {
	
		
	public Payment createPayment(
			Double total, 
			String currency, 
			String method,
			String intent,
			String description, 
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		return null;
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		return null;
	}

}
