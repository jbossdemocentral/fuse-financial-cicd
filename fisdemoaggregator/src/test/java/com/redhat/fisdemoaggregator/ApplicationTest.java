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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;


    @Test
    public void balanceTest() throws Exception {
    	
    	String responseMsg = "Tested!";

        camelContext.getRouteDefinition("gatewaybalance").adviceWith(camelContext, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("http*").skipSendToOriginalEndpoint().setBody(constant(responseMsg));
			}
		});
        
       
        ResponseEntity<String> balanceResponse = restTemplate.getForEntity("/demos/sourcegateway/balance/123456?moneysource=bitcoin", String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo("\""+responseMsg+"\"");
        
        balanceResponse = restTemplate.getForEntity("/demos/sourcegateway/balance/123456", String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo("\""+responseMsg+"\"");
    }
    
    @Test
    public void transferTest() throws Exception {
    	String responseMsg = "Tested!";

        camelContext.getRouteDefinition("gatewaytransfer").adviceWith(camelContext, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("http*").skipSendToOriginalEndpoint().setBody(constant(responseMsg));
			}
		});
        
       
        ResponseEntity<String> balanceResponse = restTemplate.getForEntity("/demos/sourcegateway/transfer/123456/50/345678?moneysource=bitcoin", String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo("\""+responseMsg+"\"");
        
        balanceResponse = restTemplate.getForEntity("/demos/sourcegateway/transfer/123456/50/345678", String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo("\""+responseMsg+"\"");
    }
    
}