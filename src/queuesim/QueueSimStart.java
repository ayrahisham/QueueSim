/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package queuesim;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author pandaboy_13
 */
public class QueueSimStart {
    
    private static DecimalFormat df2 = new DecimalFormat(".##");
    private int minute = 0;
    private ArrayList <Customers> custList = new ArrayList <Customers> ();
    private ArrayList<Servers> priServers = new ArrayList<Servers>();
    private ArrayList<Servers> secServers = new ArrayList<Servers>();
    private Queue <Customers> priQ = new LinkedList<Customers> ();
    private Queue <Customers> secQ = new LinkedList<Customers> (); 
    private ArrayList<String> list = new ArrayList<String> (); 
    private ArrayList<String> listServed = new ArrayList<String> ();
    
    public void readFile (String fileName)
    {
        Scanner read = null;
         try {
            //URL path = QueueSim.class.getResource(fileName);
            File f = new File(fileName);
            read = new Scanner (f);
        }
        catch (Exception e){
            System.out.println("Not Found! Quiting Program now");
            System.exit(0);
        }
      
      
       String s;
       
        while (read.hasNext())
        {
           s = read.nextLine();
           list.add(s);
        }
        /*
        for(int i = 0; i < list.size(); i ++)
        {
            System.out.println (list.get(i));
        }*/
    }
    
    public void initServersQueue ()
    {
        StringTokenizer token = new StringTokenizer (list.remove(0));
        
        while(token.hasMoreTokens())
        {
            int s = Integer.parseInt(token.nextToken());
            
            if(priServers.isEmpty())
            {
                for (int i = 0; i < s; i ++)
                {
                    Servers servers = new Servers ();
                    servers.serverName = "Primary Server " + (i+1);
                    priServers.add(servers);
                }
            }
            else 
            {
                for (int i = 0; i < s; i ++)
                {
                    Servers servers = new Servers ();
                    servers.serverName = "SecondaryServer " + (i + 1);
                    secServers.add(servers);
                }
            }
        }
        /* 
        for (int i = 0; i < priServers.size(); i ++)
        {
            System.out.println (priServers.get(i).serverName);
            System.out.println (secServers.get(i).serverName);
        }
        */
    }
    
    public void initCust ()
    {
        StringTokenizer token = null;
        
       for(int i = 0; i < list.size(); i ++)
       {
           Customers cust = null;
            int aTime = 0, dTime = 0, wTime = 0;
            String s = list.get(i);
            token = new StringTokenizer (s);
            while (token.hasMoreTokens())
            {
                aTime = Integer.parseInt(token.nextToken());
                dTime = Integer.parseInt(token.nextToken());
                wTime = Integer.parseInt(token.nextToken());
            }
                cust = new Customers (aTime, dTime, wTime, "Customer " + (i + 1));
                custList.add(cust);
        }
        
        /*
        for (int i = 0; i < custList.size(); i ++)
        {
            System.out.println("Customer Name : " + custList.get(i).name + " [arrived at : " + custList.get(i).arrivalTime + " " + custList.get(i).decidingTime + " " + custList.get(i).waitingTime + "]" );
        }
        */
     
    }
    
    public void startSim()
    {
        int totalServed = 0;
        int lastRequest = 0;
        boolean flag = true;
        int largestPQueue = 0;
        int largestSQueue = 0;
        double  averageService = 0;
        int maximumLofQ = 0;
        int totalTimeinPQ = 0;
        int totalTimeinSQ = 0;
        double averagePQ = 0, averageSQ = 0, averageOQ = 0; 
        double priQServed = 0, secQServed = 0;
        double minutePQ = 0, minuteSQ = 0;
        int counterP = 0, counterS = 0; 
        while (flag)
        {
            ++ minute;
              
            for(int i = 0; i < custList.size(); i ++) //get customers coming in at the same time
            {
                        //overall minute;
                       if(custList.get(i).arrivalTime == minute && custList.get(i).arrivalTime != 0)
                       {
                       //  System.out.println ("Customer Arrived At : " + custList.get(i).arrivalTime);
                         //push to queue
                        // System.out.println ("Adding to queue");
                         priQ.add(custList.get(i)); // add to queue
                         custList.get(i).currentQ = 0;
                         priQServed ++;
                         // as i add to queue, get maximum queue number 
                         if (priQ.size() >= largestPQueue)
                         {
                             largestPQueue = priQ.size();
                         }
                         //custList.get(counter).queueTime = 0;
                         //check if servers are empty;
                       }
            }
            
            //check if primary server is not free, then if not free, minus serving time and check if need to push to sec queue
            for (int i = 0; i < priServers.size(); i++)
            {
                if(priServers.get(i).free == true)
                {
                    priServers.get(i).idleTime ++;
                }
                if (priServers.get(i).free == false)
                {
                    -- priServers.get(i).servingTime;
                    if(priServers.get(i).servingTime == 0 || priServers.get(i).servingTime ==  -1)
                    {
                       // System.out.println("Finished with First Server! Pushing to second : " + priServers.get(i).servingCust.name);
                        secQ.add(priServers.get(i).servingCust);
                        //priQServed += 1;
                        priServers.get(i).servingCust.currentQ = 1;
                        secQServed++;
                        if(secQ.size() >= largestSQueue)
                        {
                            largestSQueue = secQ.size();
                        }
                        priServers.get(i).servingCust = null;
                        priServers.get(i).free = true;
                    }
                }
            }
            
            // check if secondary server is free
            for(int i = 0; i < secServers.size(); i ++)
            {
                if(secServers.get(i).free == true)
                {
                    secServers.get(i).idleTime ++;
                }
                if(secServers.get(i).free == false)
                {
                    -- secServers.get(i).servingTime;
                    if(secServers.get(i).servingTime == 0 || secServers.get(i).servingTime == -1)
                    { 
                        //System.out.println("Finished with second Server! pushing out : " + secServers.get(i).servingCust.name);
                        secServers.get(i).servingCust.currentQ = -1; 
                        listServed.add(secServers.get(i).servingCust.name);
                        secServers.get(i).servingCust = null;
                        secServers.get(i).free = true;
                        
                        ++ totalServed;
                    }
                }
            }
            
           for (int i = 0; i < priServers.size(); i ++)
           {
                  if (priServers.get(i).free == true)
                  {
                      if(!priQ.isEmpty())
                      {
                       //System.out.println("Customer being pushed from primaryQ to first server");
                      priServers.get(i).servingCust = priQ.remove();
                      priServers.get(i).servingTime = priServers.get(i).servingCust.decidingTime;
                      priServers.get(i).totalServingTime += priServers.get(i).servingCust.decidingTime;
                      priServers.get(i).servingCust.currentQ = -1;
                      priServers.get(i).free = false;
                      break;
                      }
                  }
           }  
           
           for (int i = 0; i < secServers.size(); i ++)
           {
                if (secServers.get(i).free == true)
                {
                    if(!secQ.isEmpty())
                    {
                       // System.out.println("Customer being pushed from secondQ to second Server");
                        secServers.get(i).servingCust = secQ.remove();
                        secQServed ++;
                        secServers.get(i).servingCust.currentQ = -1;
                        secServers.get(i).servingTime = secServers.get(i).servingCust.waitingTime;
                        secServers.get(i).totalServingTime += secServers.get(i).servingCust.waitingTime;
                        secServers.get(i).free = false;
                        break;
                    }
                }
           }   
           
          for (int i =0; i < custList.size(); i ++)
          {
              if(custList.get(i).currentQ == 0)
              {
                  custList.get(i).queuePTime ++;
              }
              else if (custList.get(i).currentQ == 1)
              {
                  custList.get(i).queueSTime ++;
              }
                       
          }
          if(priQ.isEmpty() && priQServed == custList.size() - 1 && counterP == 0)
          {
              minutePQ = minute;
              counterP ++;
          }
          if(secQ.isEmpty() && totalServed == custList.size()- 1 && counterS == 0)
          {
              minuteSQ = minute;
              counterS ++;
          }
                      
           if(!priQ.isEmpty())
           {
               averagePQ += priQ.size();
           }
           if(!secQ.isEmpty())
           {
               averagePQ += secQ.size();
           }
                      
           if((largestPQueue + largestSQueue) >= maximumLofQ)
           {
               maximumLofQ = largestPQueue + largestSQueue;
           }
           
           if(totalServed == custList.size() - 1)
           {
                 break;
           }
        }
        Collections.sort(listServed);
        System.out.println(" ");
        System.out.println("Number of people Served :" + totalServed);
        System.out.println ("Time Of last services request is completed : " + minute + " minutes");
        int totalServiceP =0 , totalServiceS =0;
        for (int i = 0; i < priServers.size(); i ++)
        {
            totalServiceP += priServers.get(i).totalServingTime;
        }
        for (int i = 0; i < priServers.size(); i ++)
        {
            totalServiceS += secServers.get(i).totalServingTime;
        }
        System.out.println ("Average Length of Primary Queue : " + String.format("%.2f", (priQServed / minutePQ)) + " customer / minute");
        System.out.println("Average Length of Secondary Queue : " + String.format("%.2f", (secQServed / minuteSQ)) + " customer / minute");
        System.out.println("Average Length of both Queue : " + String.format("%.2f" , ((priQServed / minutePQ) + (secQServed / minuteSQ) / 2))+ " customer / minute");
        System.out.println ("Time where PQ is empty & served all: "  + minutePQ + " * " + minuteSQ);
        
        averageService = (double) (totalServiceP + totalServiceS) / totalServed;
        
        System.out.println ("Average Total Service Time : " + averageService + " minutes/customer");
         for(Customers c : custList)
        {
           totalTimeinPQ += c.queuePTime;
           totalTimeinSQ += c.queueSTime;
        }
        System.out.println("Average Time in Primary Queue is : " + (double)totalTimeinPQ / totalServed + " minutes / customer");
        System.out.println("Average Time in Secondary Queue is : " + (double)totalTimeinSQ / totalServed + " minutes / customer");
        System.out.println ("Maximum Length of Primary Queue : " + largestPQueue);
        System.out.println ("Maximum Length of Secondary Queue : " + largestSQueue);
         System.out.println ("Maximum Length of both Queue : " + maximumLofQ);
        for (int i = 0; i < priServers.size(); i ++)
        {
            System.out.println ("Idle Time for " + priServers.get(i).serverName + " : " + priServers.get(i).idleTime);
        }
        for (int i = 0; i < secServers.size(); i ++)
        {
            System.out.println ("Idle Time for " + secServers.get(i).serverName + " : " + secServers.get(i).idleTime);
        }
//System.out.println (priQ);
       
    }
}
