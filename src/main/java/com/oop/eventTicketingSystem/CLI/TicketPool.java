package com.oop.eventTicketingSystem.CLI;

import java.util.*;

public class TicketPool {
    private List<Ticket> ticketList;
    private int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        this.ticketList = Collections.synchronizedList(new ArrayList<>());            //new LinkedList<>();

    }
    //Add ticket method which is used by Vendors to addTickets
    public synchronized void addTickets(Ticket ticket){ //Need to synchronize
        while (ticketList.size() >= maximumCapacity){
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();  //For CLI
                throw new RuntimeException(e.getMessage());
            }
        }
        this.ticketList.add(ticket); //Adding the ticket to the list
        notifyAll(); //Notifying all the waiting threads
        //Print the message to show the thread name who added and the current size of the pool
        System.out.println(Thread.currentThread().getName() + " has added a ticket to the pool. Current size is "+ticketList.size());
    }
    //buyTicket method is used when customers are buying tickets
    public synchronized Ticket buyTicket(){
        while(ticketList.isEmpty()){
            try {
                wait();
            }catch (InterruptedException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        Ticket ticket = ticketList.removeFirst();  //Remove ticket from queue (front)
        notifyAll(); //Notify all waiting threads
        //Print the message to show the thread name who added and the current size of the pool
        System.out.println(Thread.currentThread().getName()+" has bought a ticket from the pool. Current pool size is "+ticketList.size());
        return ticket;
    }
}
