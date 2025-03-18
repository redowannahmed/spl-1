package Testing;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.DuplicateUsernameException;
import org.junit.jupiter.api.Test;

public class DuplicateUsernameExceptionTest {
    @Test
    public void testExceptionMessage() {
        Exception exception = assertThrows(DuplicateUsernameException.class, () -> {
            throw new DuplicateUsernameException("Duplicate Username found");
        });
        assertEquals("Duplicate Username found", exception.getMessage());
    }
}
