package com.redhat.fisdemoblockchain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CamelContext camelContext;

	
    @Test
    public void testBalance() throws Exception {
    	
        NotifyBuilder notify = new NotifyBuilder(camelContext).create();
        notify.matches(20, TimeUnit.SECONDS);
            
    	// Then call the REST API
        ResponseEntity<String> balanceResponse = restTemplate.getForEntity("/demos/bitcoinaccount/balance/123456", String.class);
        assertThat(balanceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(balanceResponse.getBody()).isEqualTo("\"Account 123456 has balance of : 3000\"");
        
    }
    
    @Test
    @DirtiesContext
    public void testTransfer() {
    	
    	// Then call the REST API
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("amt", "30");
        headers.set("recptid", "345678");
      
        ResponseEntity<String> transferResponse = restTemplate.postForEntity("/demos/bitcoinaccount/transfer/123456", headers ,String.class);
        
        assertThat(transferResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transferResponse.getBody()).isEqualTo("\"Successfully transfered $30 from 123456 to 345678 remaining balance are: $2970\"");
    }
}