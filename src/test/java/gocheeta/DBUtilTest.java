package gocheeta;

import com.jemeisha.gocheeta.database.DBUtil;
import com.jemeisha.gocheeta.pojo.Customer;
import com.jemeisha.gocheeta.pojo.Driver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class DBUtilTest {

    DBUtil db;

    @Before
    public void setUp(){
        this.db = DBUtil.getSingletonInstance();
    }

    @Test
    public void testGetCustomer(){
        Customer c =db.getCustomerByUsername("dumindu");

        assertNotEquals(null,c);
        assertEquals("dumindu",c.getUsername());
    }

    @Test
    public void testGetAllCustomers() {
        ArrayList<Customer> list = db.getAllCustomers();
        assertNotEquals(0,list.size());
    }

    @Test
    public void getAllDriversTest(){
        ArrayList<Driver> list = db.getAllDrivers();
        assertNotEquals(0,list.size());
    }

}