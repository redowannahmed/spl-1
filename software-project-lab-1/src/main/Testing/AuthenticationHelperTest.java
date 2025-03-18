package Testing;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Scanner;
import exceptions.DuplicateIDException;
import exceptions.DuplicateUsernameException;
import user.User;
import auth.Authentication;
import auth.AuthenticationHelper;


class AuthenticationHelperTest {
    private Authentication auth;
    private AuthenticationHelper helper;

    @BeforeEach
    void setUp() {
        auth = new Authentication();
        helper = new AuthenticationHelper(auth);
    }

    @Test
    void testLoginHelper() {
        // Adding the user manually to ensure the user exists for login
        try {
            auth.registerStudent("John", "john123", 101, "123Abc");
        } catch (Exception e) {
            // Ignoring if already registered
        }

        Scanner sc = new Scanner("john123\n123Abc\n");
        User user = helper.loginHelper(sc);

        assertNotNull(user);
        assertEquals("john123", user.getUsername());
    }
}