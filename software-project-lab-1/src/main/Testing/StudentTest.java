package Testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.Student;

public class StudentTest {
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Messi", "messi1001", 1001, "messi1001");
    }

    @Test
    public void testValidStudentDetails() {
        assertEquals("Messi", student.getName(), "Student name should match");
        assertEquals(1001, student.getId(), "Student ID should match");
        assertEquals("messi1001", student.getPassword(), "Password should match");
    }

    @Test
    public void testInvalidStudentDetails() {
        assertNotEquals("Ronaldo", student.getName(), "Student name should not match");
        assertNotEquals(2001, student.getId(), "Student ID should not match");
        assertNotEquals("ronaldo2001", student.getPassword(), "Password should not match");
    }
    @Test
    void testGetId() {
       
        assertEquals(1001, student.getId());
    }}
