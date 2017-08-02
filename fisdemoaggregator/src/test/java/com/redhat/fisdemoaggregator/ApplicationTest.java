/*
 * Copyright 2005-2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.redhat.fisdemoaggregator;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;


    @Test
    public void balanceTest() throws Exception {
    	
    	
    	String accountMsg = "\"Account 123456 has balance of : 3000\"";
    	String profileMsg = "{\"id\":123456,\"acctname\":\"Simon C\",\"balance\":5000,\"ssn\":\"987655663\",\"phone\":\"7264947276\",\"addr\":\"43 SLIVER EAGLE ST, RIVER\",\"state\":\"MA\"}";

    	camelContext.getRouteDefinition("gatewaybalance").adviceWith(camelContext, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("http://fisblockchain-service/demos/bitcoinaccount/balance*").skipSendToOriginalEndpoint().setBody(constant(accountMsg));
				interceptSendToEndpoint("http://fisaccount-service/demos/account/profile*").skipSendToOriginalEndpoint().setBody(constant(profileMsg));
			}
		});
        
       
        ResponseEntity<String> balanceResponse = restTemplate.getForEntity("/demos/sourcegateway/balance/123456?moneysource=bitcoin", String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo(accountMsg);
        
        balanceResponse = restTemplate.getForEntity("/demos/sourcegateway/balance/123456", String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo("\"Account 123456 has balance of :5000\"");
    }
    
    @Test
    public void transferTest() throws Exception {
    	
    	String accountMsg = "{\"id\":123456,\"acctname\":\"Simon C\",\"balance\":4990,\"ssn\":\"987655663\",\"phone\":\"7264947276\",\"addr\":\"43 SLIVER EAGLE ST, RIVER\",\"state\":\"MA\"}";
    	String blockchainMsg = "Transfered Completed! $10 from 123456 to 234567 the remaining balance is: $2990";
    	
        camelContext.getRouteDefinition("gatewaytransfer").adviceWith(camelContext, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("http://fisblockchain-service/demos/bitcoinaccount/transfer*").skipSendToOriginalEndpoint().setBody(constant(blockchainMsg));
				interceptSendToEndpoint("http://fisaccount-service/demos/account/transfer*").skipSendToOriginalEndpoint().setBody(constant(accountMsg));
			}
		});
       
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        
        params.set("acctid", "123456");
        params.set("amt", "10");
        params.set("recptid", "234567");
        params.set("moneysource", "bitcoin");
        
        ResponseEntity<String> balanceResponse = restTemplate.postForEntity("/demos/sourcegateway/transfer", params, String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo(blockchainMsg);

        params = new LinkedMultiValueMap<String, String>();
        params.set("acctid", "123456");
        params.set("amt", "10");
        params.set("recptid", "234567");
        
        balanceResponse =  restTemplate.postForEntity("/demos/sourcegateway/transfer", params, String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo("Transfer completed, account 123456 has balance of :4990");
    }
    
}