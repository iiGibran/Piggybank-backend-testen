package com.testing.piggybank.account;

import com.testing.piggybank.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountControllerIntegrationTest {

    @Autowired
    private AccountController accountController;

    // Mocking the AccountService
    @MockBean
    private AccountService mockAccountService;

    // Test for the getAccount method
    @Test
    public void testGetAccount() {
        // Providing a mock account for the given account ID
        long accountId = 1;
        Account mockAccount = new Account();
        mockAccount.setId(accountId);
        when(mockAccountService.getAccount(accountId)).thenReturn(Optional.of(mockAccount));

        // Executing the method for the mocked account
        ResponseEntity<AccountResponse> responseEntity = accountController.getAccount(accountId);

        // Verifying the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response should be OK");
        assertEquals(accountId, responseEntity.getBody().getId(), "Account ID should match");
    }

    // Test for the getAccounts method
    @Test
    public void testGetAccountsByUserId() {
        // Providing mock accounts for the given user ID
        long userId = 1L;
        List<Account> mockAccounts = Arrays.asList(new Account(), new Account());
        when(mockAccountService.getAccountsByUserId(userId)).thenReturn(mockAccounts);

        ResponseEntity<GetAccountsResponse> responseEntity = accountController.getAccounts(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response should be OK");
        assertEquals(2, responseEntity.getBody().getAccounts().size(), "Number of accounts should be 2");
    }

    // Test for the updateAccount method
    @Test
    public void testUpdateAccount() {
        // Creating an update request
        UpdateAccountRequest updateRequest = new UpdateAccountRequest();
        updateRequest.setAccountId(1L);
        updateRequest.setAccountName("NewAccountName");

        ResponseEntity<HttpStatus> responseEntity = accountController.updateAccount(updateRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response should be OK");
    }
}
