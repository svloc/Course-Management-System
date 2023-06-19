package com.cms.payment;


import java.util.Map;


import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;


public class PaypalConfig {

	
	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public String getClientSecret() {
		return clientSecret;
	}


	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	private String clientId;
	
	private String clientSecret;
	
	private String mode;

	
	public Map<String, String> paypalSdkConfig() {
		return null;
	}

	
	public OAuthTokenCredential oAuthTokenCredential() {
		return null;
	}

	
	public APIContext apiContext() throws PayPalRESTException {
		return null;
	}

}
