package gocheeta;
import com.jemeisha.gocheeta.Logic;
import com.jemeisha.gocheeta.pojo.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class LogicTest {
    Logic logic;

    @Before
    public void setUp(){
        this.logic = new Logic();
        //Insert
    }

    @After
    public void tearDown(){
        //delete
    }

    @Test
    public void testCustomerLogin(){
        String token=null;
        try {
            token = logic.login("Sugath","1111");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        assertNotEquals(null,token);
    }

    @Test
    public void testCustomerFalseLogin(){
        String token=null;
        try {
            token = logic.login("Sugath22","1111");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(null,token);
    }

    @Test
    public void testDriverLogin(){
        String token=null;
        try {
            token = logic.loginDriver("8","1111");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        assertNotEquals(null,token);
    }

    @Test
    public void testDriverFalseLogin(){
        String token=null;
        try {
            token = logic.loginDriver("85","1111");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(null,token);
    }

    @Test
    public void testAdminLogin(){
        String token = logic.loginAdmin("admin","pass");
        assertNotEquals(null,token);
    }

    @Test
    public void testAdminFalseLogin(){
        String token = logic.loginAdmin("admin22","pass");
        assertEquals(null,token);
    }

    @Test
    public void testBookARide(){
        Order o = null;
        try {
            o = logic.bookARide("dumindu",1,4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull(o);
    }

}
