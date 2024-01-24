package com.testing.piggybank.account;

import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Direction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

// JUnit test class for AccountService
public class AccountServiceTest {

    // Mocking the AccountRepository
    @Mock
    private AccountRepository accountRepository;

    // Injecting the mocks into AccountService
    @InjectMocks
    private AccountService accountService;

    // Initialization method that runs before each test method
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // Test method for the updateBalance functionality
    @Test
    public void testUpdateBalance() {
        // Defines a mockAccount with a balance of $500 and assigns it an accountId
        long accountId = 123;
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal amount = new BigDecimal("100.00");
        Direction direction = Direction.CREDIT;

        Account mockAccount = new Account();
        mockAccount.setBalance(initialBalance);

        // Mocking the behavior of the accountRepository
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        // Calls the method "updateBalance" of the accountService with the specified parameters
        accountService.updateBalance(accountId, amount, direction);

        // Validates that the balance of the mockAccount after the updateBalance operation matches the expected balance
        BigDecimal expectedBalance = initialBalance.subtract(amount);
        assertEquals(expectedBalance, mockAccount.getBalance());

        // Verifying that the findById method
        verify(accountRepository, times(1)).findById(accountId);

        // Verifying that the save method
        verify(accountRepository, times(1)).save(any(Account.class));
    }
}
