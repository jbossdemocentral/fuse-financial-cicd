package com.redhat.myfispipeline;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void testProfile() {
        
    	// Then call the REST API
        ResponseEntity<Accounts> profileResponse = restTemplate.getForEntity("/demos/account/profile/123456", Accounts.class);
        assertThat(profileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Accounts account = profileResponse.getBody();
        assertThat(account.getAcctname()).isEqualTo("Simon C");
        assertThat(account.getSsn()).isEqualTo("987655663");
        assertThat(account.getBalance()).isEqualTo(5000);
        assertThat(account.getPhone()).isEqualTo("7264947276");
        assertThat(account.getState()).isEqualTo("MA");
        assertThat(account.getAddr()).isEqualTo("43 SLIVER EAGLE ST, RIVER");

    }
    
    @Test
    public void testTransfer(){
    	// Then call the REST API
        Map<String, String> param = new HashMap<String, String>(); 
        param.put("amt", "50");
        param.put("recepid", "345678");
        ResponseEntity<Accounts> profileResponse = restTemplate.postForEntity("/demos/account/transfer/123456", param, Accounts.class);
       
        assertThat(profileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Accounts account = profileResponse.getBody();
        assertThat(account.getAcctname()).isEqualTo("Simon C");
        assertThat(account.getSsn()).isEqualTo("987655663");
        assertThat(account.getBalance()).isEqualTo(4950);
        assertThat(account.getPhone()).isEqualTo("7264947276");
        assertThat(account.getState()).isEqualTo("MA");
        assertThat(account.getAddr()).isEqualTo("43 SLIVER EAGLE ST, RIVER");
    }
    
    @Test
    public void testSwagger() {
        ResponseEntity<String> swaggerResponse = restTemplate.getForEntity("/demos/api-docs", String.class);
        assertThat(swaggerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}