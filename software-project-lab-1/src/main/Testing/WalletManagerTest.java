package Testing;

import auth.Authentication;
import user.Student;
import wallet.WalletManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WalletManagerTest {

    // Mock Authentication for testing purposes
    private class MockAuthentication extends Authentication {
        // You can create a mock or simple implementation for testing
        @Override
        public Student findStudentByUsername(String username) {
            // Dummy student for testing
            return new Student("John Doe", username, 12345, "password");
        }
    }

    @Test
    void testIsDuplicateSlipId_NoDuplicates() {
        WalletManager manager = new WalletManager(new MockAuthentication());
        assertFalse(manager.isDuplicateSlipId("unique-slip-id-1234"));
    }

    @Test
    void testRequestRecharge_Success() {
        WalletManager manager = new WalletManager(new MockAuthentication());
        String slipId = WalletManager.generateUniqueSlipID();
        boolean success = manager.requestRecharge("testuser", 100, slipId);
        assertTrue(success, "Recharge request should succeed with valid data.");
    }

    @Test
    void testRequestRecharge_InvalidAmount() {
        WalletManager manager = new WalletManager(new MockAuthentication());
        String slipId = WalletManager.generateUniqueSlipID();
        boolean success = manager.requestRecharge("testuser", -50, slipId);
        assertFalse(success, "Recharge request should fail with negative amount.");
    }
}
