package com.testing.piggybank.account;

import com.testing.piggybank.model.Currency;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.GetTransactionsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testGetTransactions() {
        long accountId = 123;
        String url = "http://localhost:" + port + "/api/v1/transactions/" + accountId;

        // Making a GET request to the specified URL
        ResponseEntity<GetTransactionsResponse> responseEntity =
                restTemplate.getForEntity(url, GetTransactionsResponse.class);

        // Asserting that the HTTP status code is 200 (OK)
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

        GetTransactionsResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();

        // Asserting that the list of transactions in the response is empty for this account
        assertThat(response.getTransactions()).isEmpty();
    }

    @Test
    public void testCreateTransaction() {
        // Setting up the URL for the POST request
        String url = "http://localhost:" + port + "/api/v1/transactions";

        // Creating a valid transaction request
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setSenderAccountId(1L); // Provide valid account IDs
        request.setReceiverAccountId(2L);
        request.setAmount(BigDecimal.valueOf(100.0)); // Provide a valid amount
        request.setCurrency(Currency.valueOf("USD"));
        request.setDescription("Test Transaction");

        // Sending a POST request to create a transaction
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, request, Void.class);

        // Asserting that the HTTP status code is 200 (OK) or any other expected status for a successful transaction
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }
}
