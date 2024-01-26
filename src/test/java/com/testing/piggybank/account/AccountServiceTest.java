package com.testing.piggybank.account;

import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Direction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    // Mocking the AccountRepository
    @Mock
    private AccountRepository accountRepository;

    // Injecting the mocks into AccountService
    @InjectMocks
    private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Test method for getting a specific account
    @Test
    public void testGetAccount() {
        long accountId = 123;
        Account mockAccount = new Account();
        mockAccount.setId(accountId);

        // Mocking the behavior of the accountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        // Calling the method "getAccount" of the accountService
        Optional<Account> result = accountService.getAccount(accountId);

        // Checking that the result has the correct ID
        assertTrue(result.isPresent());
        assertEquals(accountId, result.get().getId());
        verify(accountRepository, times(1)).findById(accountId);
    }

    // Test method for getting accounts by user ID
    @Test
    public void testGetAccountsByUserId() {
        long userId = 1L;
        List<Account> mockAccounts = Arrays.asList(new Account(), new Account());

        // Mocking the behavior of the mocked account
        when(accountRepository.findAllByUserId(userId)).thenReturn(mockAccounts);

        // Calling the method "getAccountsByUserId" of the accountService
        List<Account> result = accountService.getAccountsByUserId(userId);

        // Checking if that the result has the expected number of accounts
        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAllByUserId(userId);
    }

    // Test method for updating the balance with a credit operation
    @Test
    public void testUpdateBalanceCredit() {
        long accountId = 123;
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal amount = new BigDecimal("100.00");
        Direction direction = Direction.CREDIT;

        Account mockAccount = new Account();
        mockAccount.setBalance(initialBalance);

        // Mocking the behavior of the mocked account
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        // Calling the method "updateBalance"
        accountService.updateBalance(accountId, amount, direction);

        // Verifying that the balance was updated correctly
        BigDecimal expectedBalance = initialBalance.subtract(amount);
        assertEquals(expectedBalance, mockAccount.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    // Test method for updating the balance with a debit operation
    @Test
    public void testUpdateBalanceDebit() {
        long accountId = 123;
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal amount = new BigDecimal("100.00");
        Direction direction = Direction.DEBIT;

        Account mockAccount = new Account();
        mockAccount.setBalance(initialBalance);

        // Mocking the behavior of the accountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        // Calling the method "updateBalance"
        accountService.updateBalance(accountId, amount, direction);

        // Checking that the balance was updated correctly
        BigDecimal expectedBalance = initialBalance.add(amount);
        assertEquals(expectedBalance, mockAccount.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }
}
