package com.cms.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaypalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    /**
     * Returns the configuration map for PayPal SDK.
     *
     * @return PayPal SDK configuration map
     */
    @Bean
    public Map<String, String> paypalSdkConfig() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        return configMap;
        // return null;
    }

    /**
     * Returns the OAuthTokenCredential required for PayPal API authentication.
     *
     * @return OAuthTokenCredential instance
     */
    @Bean
    public OAuthTokenCredential oAuthTokenCredential() {
        return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
        // return null;
    }

    /**
     * Returns the APIContext for making PayPal API calls.
     *
     * @return APIContext instance
     * @throws PayPalRESTException if an error occurs while retrieving the access token
     */
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(paypalSdkConfig());
        return context;
        // return null;
    }

}
