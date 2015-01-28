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

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

/**
 *
 * @author Pavlo Gudzenko <pavlo.gudzenko@gmail.com>
 */
public class OrderQueue {
    Queue<Order> orderQueue = new ArrayDeque<>();
    
    public void add(Order order) {   
        orderQueue.add(order);
        order.setTimeReceived(new Date());
    }
    
    public String chekingIfOrdersExist(Queue<Order> orderQueue){
      String isNull = "";
         if (orderQueue.isEmpty())  isNull = null;       
        return isNull;
    }
    
    public Order theEarliestOrder(Queue<Order> OQ){
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
