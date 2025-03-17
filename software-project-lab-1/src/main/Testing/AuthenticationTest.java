package Testing;


import auth.Authentication;
import exceptions.DuplicateIDException;
import exceptions.DuplicateUsernameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.Admin;
import user.Student;
import user.User;


import static org.junit.jupiter.api.Assertions.*;


class AuthenticationTest {
    private Authentication auth;


    @BeforeEach
    void setUp() {
        auth = new Authentication();
    }


    @Test
    void testAdminLogin() {
        User user = auth.login("admin", "admin123");
        assertNotNull(user);
        assertTrue(user instanceof Admin);
        assertEquals("admin", user.getUsername());
    }


    @Test
    void testRegisterStudent() throws DuplicateUsernameException, DuplicateIDException {
        auth.registerStudent("Dwayne Johnson", "dwaynejohnson", 2001, "Dwayne123");
        Student student = auth.findStudentByUsername("dwaynejohnson");
        assertNotNull(student);
        assertEquals("Dwayne Johnson", student.getName());
        assertEquals(2001, student.getId());
    }


    @Test
    void testLoginValidStudent() throws DuplicateUsernameException, DuplicateIDException {
        auth.registerStudent("The Rock", "therock", 2002, "Rocky123");
        User user = auth.login("therock", "Rocky123");
        assertNotNull(user);
        assertTrue(user instanceof Student);
        assertEquals("therock", user.getUsername());
    }


    @Test
    void testIsStudentUsernameUnique() throws DuplicateUsernameException, DuplicateIDException {
        auth.registerStudent("Iron Man", "ironman", 2003, "IronMan123");
        assertFalse(auth.isStudentUsernameUnique("ironman"));
        assertTrue(auth.isStudentUsernameUnique("newuser"));
    }


    @Test
    void testIsValidPassword() {
        assertTrue(Authentication.isValidPassword("Dwayne123"));
        assertFalse(Authentication.isValidPassword("short"));
    }


    @Test
    void testCustomHash() {
        String hash1 = Authentication.customHash("password123");
        String hash2 = Authentication.customHash("password123");
        assertEquals(hash1, hash2);
    }
}

