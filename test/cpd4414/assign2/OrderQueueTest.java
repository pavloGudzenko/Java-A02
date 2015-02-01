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

import cpd4414.assign2.Order;
import cpd4414.assign2.OrderQueue;
import cpd4414.assign2.noCustomerIdAndNameException;
import cpd4414.assign2.Purchase;
import cpd4414.assign2.noListOfPurchaseException;
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

    // Time Received = now
    @Test 
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws noCustomerIdAndNameException, noListOfPurchaseException {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);

        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    
      // No Customer Name, no CustomerId
        @Test
    public void testWhenNeitherCustomerIdNorCustomerNameExistsThrowException() throws noCustomerIdAndNameException, noListOfPurchaseException {
        boolean exceptionFlag = false;
        try {
            OrderQueue orderQ = new OrderQueue();

            Order order = new Order(null, null);
            order.addPurchase(new Purchase("PROD0004", 450));
            order.addPurchase(new Purchase("PROD0006", 250));
            orderQ.add(order);
        } catch (noCustomerIdAndNameException ex) {
            exceptionFlag = true;
//            assertEquals("The Customer ID and/or Customer Name are not exist' ", ex.message());
        }
        assertTrue(exceptionFlag);
    }
    
    
    // No List Of Purchases
     @Test
    public void testWhenthereIsNoListOfPurchasesThenThrowException() throws noCustomerIdAndNameException {
        boolean exceptionFlag = false;
        try {
            OrderQueue orderQ = new OrderQueue();

            Order order = new Order("CUST00002", "Welding&Steel");
            orderQ.add(order);
        } catch (noListOfPurchaseException ex) {
            exceptionFlag = true;
        }
        assertTrue(exceptionFlag);
    }
    
    
    // Request for Next Order. Return with the Earliest time received;
        @Test
    public void testGivenRequestForNextOrderReturnOrderWithTheEarliestTimeReceived() throws noCustomerIdAndNameException, noListOfPurchaseException {
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
        
        Order expResult = order1;
        Order realResult = orderQ.requestForOrder();
        
        assertEquals(expResult, realResult);

    }

   //  Request for Next Order. When There are no orders Return NULL;
    @Test
    public void testWhenThereAreNoOrdersInTheSystemReturnNull() {
        OrderQueue orderQ = new OrderQueue();
        Order isThereAnyOrders = orderQ.requestForOrder();
        assertSame(null, isThereAnyOrders);
    }
    
    //  Request for Next Order. When The Order daes not have Time Received;
     @Test
    public void testWhenTheOrderDoesNotHaveTimeReceivedThrowException() throws noCustomerIdAndNameException, noListOfPurchaseException{
         boolean isTimeReceived = false;
        try{
        OrderQueue orderQ = new OrderQueue();   
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));       
         orderQ.add(order);
         Order requestedOrder = orderQ.requestForOrder();
         orderQ.process(requestedOrder);
 
        } catch (noTimeReceivedException ex){
           isTimeReceived = true;  
        }
         assertFalse(isTimeReceived);
    }    

    
    @Test
    public void testRequestFulfillWhenOrderDoesNotHaveTimeProcessedThrowException() throws noCustomerIdAndNameException, noListOfPurchaseException, noTimeProcessedException, noTimeReceivedException{
        boolean isTimeReceived = false;
        try{
        OrderQueue orderQ = new OrderQueue();   
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));       
        orderQ.add(order);
        orderQ.process(orderQ.orderQueue.element());
        orderQ.fulfill();
         
 
       } catch (noTimeReceivedException ex){
           isTimeReceived = true;  
        }
         assertFalse(isTimeReceived);
    
    }
    
     @Test
    public void testRequestFulfillWhenOrderDoesNotHaveTimeReceivedThrowException() throws noCustomerIdAndNameException, noListOfPurchaseException, noTimeProcessedException, noTimeReceivedException{
        boolean isTimeProcessed = false;
        try{
        OrderQueue orderQ = new OrderQueue();   
        Order order = new Order("CUST00001", "ABC Construction");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));       
        orderQ.add(order);
        orderQ.fulfill();
         
 
        } catch (noTimeProcessedException ex){
           isTimeProcessed = true;  
        }
         assertTrue(isTimeProcessed);
    
    }


    @Test
    public void testWhenOrderHasTimeReceivedAndPurchasesAreInInventoryTable() {

    }


   
}
