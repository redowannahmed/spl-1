package Testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import token.MenuManagement;

import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class MenuManagementTest {
    @BeforeEach
    void setUp() {
        // Initialize menuData manually
        MenuManagement.menuData = new String[][]{
                {"Saturday", "Pancakes", "Chicken Rice", "Steak"},
                {"Sunday", "Omelette", "Beef Curry", "Pizza"},
                {"Monday", "Cereal", "Fish & Chips", "Burger"},
                {"Tuesday", "Toast", "Pasta", "Soup"},
                {"Wednesday", "Eggs", "Salad", "Sushi"},
                {"Thursday", "Waffles", "BBQ Chicken", "Tacos"},
                {"Friday", "Bagel", "Grilled Cheese", "Lasagna"}
        };
    }

    @Test
    void testLoadMenus() {
        assertEquals("Pancakes", MenuManagement.menuData[0][1]);
        assertEquals("Beef Curry", MenuManagement.menuData[1][2]);
    }

    

    
 
}
