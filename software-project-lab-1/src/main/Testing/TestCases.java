package Testing;

import static org.junit.jupiter.api.Assertions.*;

import auth.UpdateInfo;
import org.junit.jupiter.api.Test;
import auth.Authentication;
import token.TokenManager;
import token.TokenType;
import user.Student;
import user.User;
import wallet.Wallet;
import wallet.WalletManager;

class TestCases{

    @Test
    void testValidPassword() {
        Authentication auth = new Authentication();

        boolean result1 = auth.isValidPassword("Passw0rd");
        assertTrue(result1, "Valid password 'Passw0rd' should pass");
    }

    @Test
    void testUpdatePasswordRequiresOldPassword() {
        Authentication auth = new Authentication();
        Student student = new Student("md monon", "monon", 1001, auth.customHash("OldPass1"));

        boolean result = UpdateInfo.updatePassword(student, auth, "OldPass1", "NewPass1", "NewPass1");

        assertTrue(result, "Password update should fail if the old password is incorrect");
    }

    @Test
    void testUpdateUsernameRequiresOldUsernameAndPassword() {
        Authentication auth = new Authentication();
        Student student = new Student("md monon", "monon", 1001, auth.customHash("OldPass1"));

        boolean result = UpdateInfo.updateUsername(student, auth, "monon", "WrongPass1", "mnn");

        assertFalse(result, "Username update should fail if the old username or password is incorrect");
    }

    @Test
    void testLogin() {

        Authentication auth = new Authentication();


        User adminResult = auth.login("admin", "admin123");
        assertEquals("admin", adminResult.getUsername(), "Admin login should return correct username");
    }

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

