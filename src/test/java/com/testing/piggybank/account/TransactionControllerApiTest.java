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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetTransactions() {
        long accountId = 123L;
        String url = "http://localhost:" + port + "/api/v1/transactions/" + accountId;
        ResponseEntity<GetTransactionsResponse> responseEntity =
                restTemplate.getForEntity(url, GetTransactionsResponse.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        GetTransactionsResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getTransactions()).isEmpty();
    }

    @Test
    public void testCreateTransaction() {
        String url = "http://localhost:" + port + "/api/v1/transactions";

        CreateTransactionRequest request = new CreateTransactionRequest();

        // sending post request to make a transactoin test
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, request, Void.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

}
