package com.cms.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Component
@Service
public class PaypalService {

	@Autowired
	private APIContext apiContext;

	/**
	 * Creates a PayPal payment.
	 *
	 * @param total       Total amount of the payment
	 * @param currency    Currency code (e.g., "USD")
	 * @param method      Payment method (e.g., "paypal")
	 * @param intent      Payment intent (e.g., "sale")
	 * @param description Payment description
	 * @param cancelUrl   Cancel URL for the payment
	 * @param successUrl  Success URL for the payment
	 * @return Created Payment object
	 * @throws PayPalRESTException if an error occurs while creating the payment
	 */
	public Payment createPayment(Double total, String currency, String method,
			String intent, String description,
			String cancelUrl, String successUrl) throws PayPalRESTException {

		Amount amount = new Amount();
		amount.setCurrency(currency);
		total = new BigDecimal(total).setScale(2,
				RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);

		return payment.create(apiContext);
		// return null;
	}

	/**
	 * Executes a PayPal payment.
	 *
	 * @param paymentId PayPal payment ID
	 * @param payerId   PayPal payer ID
	 * @return Executed Payment object
	 * @throws PayPalRESTException if an error occurs while executing the payment
	 */
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
		// return null;
	}

}
