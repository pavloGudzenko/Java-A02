/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cpd4414.assign2;


import org.junit.Test;
import static org.junit.Assert.*;
import cpd4414.assign2.noTimeReceivedException;


/**
 *
 * @author c0650853
 */
public class OrderTest {
    
    public OrderTest() {
    }

        @Test
    public void testWhenTheOrderDoesNotHaveTimeReceivedThrowException(){
        try{
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));       
        order.processOrder(order);
        } catch (noTimeReceivedException ex){
            assertEquals("The order does not have a 'time received' ", ex.message());
        }

    }    
}
