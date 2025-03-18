package Testing;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.DuplicateIDException;
import org.junit.jupiter.api.Test;

public class DuplicateIDExceptionTest {
    @Test
    public void testExceptionMessage() {
        Exception exception = assertThrows(DuplicateIDException.class, () -> {
            throw new DuplicateIDException("Duplicate ID found");
        });
        assertEquals("Duplicate ID found", exception.getMessage());
    }
}
