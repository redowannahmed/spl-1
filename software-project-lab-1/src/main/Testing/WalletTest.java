package Testing;

import org.junit.jupiter.api.Test;
import wallet.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest {

    @Test
    void testInitialBalanceIsZero() {
        Wallet wallet = new Wallet();
        assertEquals(0, wallet.getBalance(), "Initial balance should be 0");
    }

    @Test
    void testAddBalance() {
        Wallet wallet = new Wallet();
        wallet.addBalance(100);
        assertEquals(100, wallet.getBalance(), "Balance should be 100 after adding 100");
    }

    @Test
    void testAddNegativeBalanceDoesNothing() {
        Wallet wallet = new Wallet();
        wallet.addBalance(-50);
        assertEquals(0, wallet.getBalance(), "Adding negative amount should not change balance");
    }

    @Test
    void testDeductBalance() {
        Wallet wallet = new Wallet();
        wallet.addBalance(200);
        wallet.deductBalance(50);
        assertEquals(150, wallet.getBalance(), "Balance should be 150 after deducting 50");
    }
}
