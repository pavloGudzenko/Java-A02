/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.assign2;

import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.Purchase;
import cpd4414.assign2.Order;
import cpd4414.assign2.noTimeReceivedException;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Pavlo Gudzenko <pavlo.gudzenko@gmail.com>
 */
public class OrderQueueTest {

    public OrderQueueTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
 
        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }

    @Test
    public void testWhenThereAreNoOrdersInTheSystemReturnNull() {
        OrderQueue orderQ = new OrderQueue();
        String isThereAnyOrders = orderQ.chekingIfOrdersExist(orderQ.orderQueue);
        assertSame(null, isThereAnyOrders);
    }

    @Test
    public void testWhenThereAreOrdersInSystemReturnOrderWithTheEarliestTimeReceivedWithNoTimeProcessed() {
        OrderQueue orderQ = new OrderQueue();

        // adding order#1
        Order order1 = new Order("CUST00001", "ABC Construction");
        order1.addPurchase(new Purchase("PROD0004", 450));
        order1.addPurchase(new Purchase("PROD0006", 250));
        orderQ.add(order1);

        // adding order#2
        Order order2 = new Order("CUST00002", "Fozzy Group");
        order2.addPurchase(new Purchase("PROD0012", 50));
        orderQ.add(order2);

        // adding order#3
        Order order3 = new Order("CUST00002", "Welding&Steel");
        order3.addPurchase(new Purchase("PROD0012", 150));
        orderQ.add(order2);

        Order expResult = orderQ.orderQueue.element();
 
        Order result = orderQ.theEarliestOrder(orderQ.orderQueue);
        assertEquals(expResult, result);

    }
    
    @Test
    public void testWhenOrderHasTimeReceivedAndPurchasesAreInInventoryTable(){
      
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
