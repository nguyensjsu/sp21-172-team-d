package com.example.springstarbucksapi;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class OrderController {
    private final OrderRepository repository;
    private HashMap<String, Order> orders = new HashMap<>();

    @Autowired
    private CardRepository cardRepository; 

    @Autowired
    private CustomerRepository customerRepository;

    class Message {
        private String status;
        
        public String getStatus() {
            return status;
        }
        public void setStatus(String msg) {
            status = msg;
        }
    }

    OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.CREATED)
    Order newOrder(@PathVariable String regid, @RequestBody Order order) {
        System.out.println("placing order " + regid + ": " + order);

        if(order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")) 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order Request");

        Order active = orders.get(regid);
        if(active != null) {
            System.out.println("Active Order " + regid + ": " + active);
            if(order.getStatus().equals("Ready for Payment"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active Order Exists");

        }
        double price = 0.0;
        switch (order.getDrink()) {
        case "Caffe Latte":
            switch (order.getSize()) {
            case "Tall":
                price = 2.95;
                break;
            case "Grande":
                price = 3.95;
                break;
            case "Venti":
                price = 4.25;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size");   
            }
            break;
        case "Caffe Mocha":
            switch (order.getSize()) {
            case "Tall":
                price = 2.95;
                break;
            case "Grande":
                price = 3.95;
                break;
            case "Venti":
                price = 4.25;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size");
            }
            break;
        case "Caffe Americano":
            switch (order.getSize()) {
            case "Tall":
                price = 2.25;
                break;
            case "Grande":
                price = 2.75;
                break;
            case "Venti":
                price = 3.00;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size");
            }
            break;
        case "Cappuccino":
            switch (order.getSize()) {
            case "Tall":
                price = 2.95;
                break;
            case "Grande":
                price = 3.65;
                break;
            case "Venti":
                price = 3.95;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size");
            }
            break;
        case "Espresso":
            switch (order.getSize()) {
            case "Short":
                price = 1.75;
                break;
            case "Tall":
                price = 1.95;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Size");
            }
            break;
        default:
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Drink");
    }
        double tax = 0.09;
        double total = price + (price * tax);
        double scale = Math.pow(10, 2);
        double rounded = Math.round(total * scale) / scale;
        order.setTotal(rounded);
        order.setStatus("Ready for Payment");
        Order new_order = repository.save(order);
        orders.put(regid, new_order);
        return new_order;
    }

    @GetMapping("/order/register/{regid}") 
    Order getOrder(@PathVariable String regid) {
        Order order = orders.get(regid);
        if(order == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found");
        }
        return order;
    }

    @DeleteMapping("/order/register/{regid}")
    Message deleteOrder(@PathVariable String regid, HttpServletResponse res) {
        repository.delete(orders.get(regid));
        orders.remove(regid);
        Message msg = new Message();
        msg.setStatus("Active Order Cleared!");
        return msg;
    }

    @PostMapping("/order/register/{regid}/pay/{cardnum}")
    StarbucksCard pay(@PathVariable String regid, @PathVariable String cardnum) {
        StarbucksCard card = cardRepository.findByCardNumber(cardnum);
        Order order = orders.get(regid);
        if(card == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Not Found");
        }
        if(order == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found");
        }
        double balance = card.getBalance();
        double price = order.getTotal();
        double newBalance = balance - price;
        card.setBalance(newBalance);
        cardRepository.save(card);
        order.setStatus("Paid with Card: " + card.getCardNumber() + " Balance: $" + card.getBalance());
        repository.save(order);
        return card;
    }

    @GetMapping("/orders")
    List<Order> allOrders() {
        return repository.findAll();
    }

    @DeleteMapping("/orders")
    Message deleteOrders() {
        repository.deleteAll();
        Message msg = new Message();
        msg.setStatus("All Orders Cleared!");
        return msg;
    }
}
