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

import cpd4414.assign2.noCustomerIdAndNameException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Pavlo Gudzenko <pavlo.gudzenko@gmail.com>
 */
public class OrderQueue {

    Queue<Order> orderQueue = new ArrayDeque<>();
    List<Order> fulfildList = new ArrayList<>(); 
    

    // adding new Order
    public void add(Order order) throws noCustomerIdAndNameException, noListOfPurchaseException {
         if (order.getCustomerId() == null || order.getCustomerName() == null || order.getCustomerId() == "" || order.getCustomerName() == "") {
            throw new noCustomerIdAndNameException();
         }
             if (order.getListOfPurchases().isEmpty()) {
            throw new noListOfPurchaseException();
        }
        
        orderQueue.add(order);
        order.setTimeReceived(new Date());
    }
    
    

    // request for Order
    public Order requestForOrder() {
        if (orderQueue.isEmpty()) {
            return null;
        } else {
            return orderQueue.element();
        }
    }
        
        

    // processing Orders
    public void process(Order order) throws noTimeReceivedException {
       if (order.getTimeReceived() == null) throw new noTimeReceivedException();
        else order.setTimeProcessed(new Date());
    }
    
    
    public void fulfill() throws noTimeProcessedException, noTimeReceivedException{
         if (orderQueue.element().getTimeProcessed() == null) {
          throw new noTimeProcessedException();
         } else 
             if (orderQueue.element().getTimeReceived() == null) {
             throw new noTimeReceivedException();
             } else
         {
           fulfildList.add(orderQueue.element());
           orderQueue.remove();
         }
        
    }
    

    public Order theEarliestOrder(Queue<Order> OQ) {
        Order result = null;
        for (int i = 0; i < OQ.size(); i++) {
            if (OQ.element().getTimeProcessed() == null) {
                result = OQ.element();
                break;
            } else {
                OQ.remove();
            }
        }
        return result;
    }
    
}    


class noTimeReceivedException extends Exception{
    public String message(){
      return "The order does not have a 'time received' ";
    }
}

class noCustomerIdAndNameException extends Exception {

    public String message() {
        return "The Customer ID and/or Customer Name are not exist' ";
    }
}

class noListOfPurchaseException extends Exception {

    public String message() {
        return "The List Of Purchases is empty";
    }
}

class noTimeProcessedException extends Exception {}
