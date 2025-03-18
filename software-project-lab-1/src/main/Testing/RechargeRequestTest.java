package Testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import wallet.*;

public class RechargeRequestTest {

    @Test
    public void testRechargeRequestConstructor() {
        RechargeRequest request = new RechargeRequest("john123", 100, "slip123");
        assertEquals("john123", request.getUsername());
        assertEquals(100, request.getAmount());
        assertEquals("slip123", request.getSlipId());
        assertEquals(LocalDate.now(), request.getDate());
    }

    @Test
    public void testToString() {
        RechargeRequest request = new RechargeRequest("john123", 100, "slip123");
        String expected = "john123,100,slip123," + LocalDate.now();
        assertEquals(expected, request.toString());
    }
}
