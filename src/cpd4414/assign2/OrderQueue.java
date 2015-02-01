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
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import org.json.simple.JSONObject;

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
        if (order.getTimeReceived() == null) {
            throw new noTimeReceivedException();
        }
        for (int index = 0; index < order.getListOfPurchases().size(); index++) {
            int quantity = Inventory.getQuantityForId(order.getListOfPurchases().get(index).getProductId());
            if (quantity > -1) {
                order.setTimeProcessed(new Date());
            }
        }

    }

    public void fulfill(Order order) throws noTimeProcessedException, noTimeReceivedException {
        if (order.getTimeProcessed() == null) {
            throw new noTimeProcessedException();
        } else if (order.getTimeReceived() == null) {
            throw new noTimeReceivedException();
        } else {
               for (int index = 0; index < order.getListOfPurchases().size(); index++) {
            int quantity = Inventory.getQuantityForId(order.getListOfPurchases().get(index).getProductId());
            if (quantity > -1) {
                order.setTimeFulfilled(new Date());
            }
        }
            this.fulfildList.add(order);
            this.orderQueue.remove();
        }

    }

    public String report() throws noOrdersInSystemException, IOException {
        if (fulfildList.isEmpty()) {
            throw new noOrdersInSystemException();
        }
        String report = "{\"orders\":[";
        for (int i = 0; i < fulfildList.size(); i++) {
            report = report + toJSON(fulfildList.get(i));
            if (i < fulfildList.size() - 1) {
                report = report + ",";
            }
        }
        return report;
    }

    public String toJSON(Order order) throws IOException {
        JSONObject JSON = new JSONObject();
        JSON.put("customerId", order.getCustomerId());
        JSON.put("customerName", order.getCustomerName());
        JSON.put("timeReceived", order.getTimeReceived());
        JSON.put("timeProcessed", order.getTimeProcessed());
        JSON.put("timeFulfilled", order.getTimeFulfilled());
        System.out.print("\"purchases\":[");
        for (int i = 0; i < order.getListOfPurchases().size(); i++) {
            System.out.print("{");
            JSON.put("productId", order.getListOfPurchases().get(i).getProductId());
            JSON.put("quantity", order.getListOfPurchases().get(i).getQuantity());
            System.out.print("}");
            if (i < order.getListOfPurchases().size() - 1) {
                System.out.print(",");
            }
        }
        JSON.put("notes", order.getNotes());
        StringWriter output = new StringWriter();
        JSON.writeJSONString(output);

        String result = output.toString();
        return result;
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

class noTimeReceivedException extends Exception {

    public String message() {
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

class noTimeProcessedException extends Exception {
}

class noOrdersInSystemException extends Exception {
}
