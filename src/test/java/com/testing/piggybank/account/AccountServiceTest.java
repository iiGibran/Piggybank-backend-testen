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

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateBalance() {
        //Defines a mockAccount with a balance of $500 and it gives it an accountId
        long accountId = 123;
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal amount = new BigDecimal("100.00");
        Direction direction = Direction.CREDIT;

        Account mockAccount = new Account();
        mockAccount.setBalance(initialBalance);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        //calls the method "updateBalance" of the accountService.java with the parameters acountId, amount and direction
        accountService.updateBalance(accountId, amount, direction);

        //Validates that the balance of the mockAccount after the updateBalance operation matches the expected balance
        BigDecimal expectedBalance = initialBalance.subtract(amount);  // Corrected line
        assertEquals(expectedBalance, mockAccount.getBalance());
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(any(Account.class));

    }
}
