
import java.util.*;

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
public class QueueSim {
    //public static ArrayList <String> list = new ArrayList<String> ();
    public String fileName;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner read = new Scanner (System.in);
        String fileName = null;
        
        if(args.length == 0)
        {
            System.out.print("Please Enter File to read :");
            fileName = read.nextLine();
           // System.out.println (fileName);
        }
        else 
        {
            fileName = args[0];
        }
        
        QueueSimStart sim = new QueueSimStart ();
        sim.readFile (fileName);
        sim.initServersQueue ();
        sim.initCust();
        sim.startSim();
    }
    
    
}
