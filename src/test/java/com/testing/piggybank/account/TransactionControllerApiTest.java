package com.testing.piggybank.account;

import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.GetTransactionsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerApiTest {

    // Injecting local server port
    @LocalServerPort
    private int port;

    // Autowiring the TestRestTemplate to interact with the API
    @Autowired
    private TestRestTemplate restTemplate;

    // Test method for GET endpoint to retrieve transactions
    @Test
    public void testGetTransactions() {
        // Setting up test data
        long accountId = 123;
        String url = "http://localhost:" + port + "/api/v1/transactions/" + accountId;

        // Making a GET request to the specified URL
        ResponseEntity<GetTransactionsResponse> responseEntity =
                restTemplate.getForEntity(url, GetTransactionsResponse.class);

        // Asserting that the HTTP status code is 200 (OK)
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        GetTransactionsResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();

        // Asserting that the list of transactions in the response is empty
        assertThat(response.getTransactions()).isEmpty();
    }

    // Test method for POST endpoint to create a transaction
    @Test
    public void testCreateTransaction() {
        // Setting up the URL for the POST request
        String url = "http://localhost:" + port + "/api/v1/transactions";

        // Creating an empty transaction request
        CreateTransactionRequest request = new CreateTransactionRequest();

        // Sending a POST request to create a transaction
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, request, Void.class);

        // Asserting that the HTTP status code is 500
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
}
