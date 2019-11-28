/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package queuesim;

/**
 *
 * @author pandaboy_13
 */
public class Customers {
       public int decidingTime;
        public int waitingTime;
        public int arrivalTime;
        public int serviceWaitTime;
   //     public String name;
        public int queuePTime = 0;
        public int queueSTime = 0;
        public int currentQ = -1;
        public String name;
        
        public Customers (int aTime, int dTime, int wTime, String name)
        {
            decidingTime = dTime;
            waitingTime = wTime;
            arrivalTime = aTime;
            serviceWaitTime = 0;
            this.name = name;
        }
        public String getName ()
        {
            return this.name;
        }
}
