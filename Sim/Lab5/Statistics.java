/*  Statistics.java

    The Statistics for Phase II of the traffic program
    Keeps track of throughput and percentage of drivers within 5 of their preferred speed.
    
    Note that toString(Vector) is a static method used by Experimenter to print ALL the stats.
        
    Author: JRL 11/00

*/
import java.util.*;

public class Statistics {
    int numberOfCars;
    int driverType;
    
    int throughput;
    double withinFive;
    int getThroughput() {return throughput;}
    double getWithinFive() {return withinFive;}
    
    public Statistics (int n, int type) {
        numberOfCars = n;
        driverType = type;
    }
    
    public String oldtoString() {
        return driver(driverType) + " driver. n=" + numberOfCars + "\t"
            + "throughput=" + throughput + "  %within 5=" 
            + withinFive/(numberOfCars*1000.0) + "\n";
    }
    
    static public int MAX_VEHICLES=300;
    static public String toString(Vector v) {
        String returnMe="Throughput\n            \t10\t30\t50\t70\t90\t110\t130\t150\n";
        for (int type=View.IDEE; type<=View.MAX; type++) {
            returnMe += driver(type) + "\t";
            for (int n=10; n<=MAX_VEHICLES; n+=10) {
                returnMe += throughputOf(v, n, type) + "\t";
            }
            returnMe += "\n";
        }
        
        returnMe += "\nwithin 5 mph of desired speed\n";           // formatting
        for (int type=View.IDEE; type<=View.MAX; type++) {
            returnMe += driver(type) + "\t";
            for (int n=10; n<=MAX_VEHICLES; n+=10) {
                returnMe += (int) (100*within5Of(v, n, type)/(n*1000.0)) + "\t";
            }
            returnMe += "\n";
        }
        
        return returnMe;
    }
            
    //  Find and return the data for a particular experiment
    static int throughputOf(Vector v, int n, int type) {  // return the throughput for n drivers of type
        for (Enumeration e=v.elements(); e.hasMoreElements();) {
            Statistics stats = (Statistics) e.nextElement();
            if (stats.getNumberOfCars() == n && stats.getDriverType() == type) {  // match?
                return stats.getThroughput();                                     // yes! done.
            }
        }
        return -1; // error
    }
    
    static double within5Of(Vector v, int n, int type) {
        for (Enumeration e=v.elements(); e.hasMoreElements();) {
            Statistics stats = (Statistics) e.nextElement();
            if (stats.getNumberOfCars() == n && stats.getDriverType() == type) {
                return stats.getWithinFive();
            }
        }
        return -1; // error
    }
    
    
    static String driver(int driverType) {
        switch (driverType) {
            case View.AVE:  return "just average";
            case View.IDEE: return "  idee fixee";
            case View.OWN:  return "I own it all";
            case View.MAX:  return "Max HeadRoom";
        }
        return "error, bad driver type: " + driverType;
    }
    
    int getNumberOfCars() {return numberOfCars;}
    int getDriverType() {return driverType;}
    
    void incThrough() {
        throughput++;
    }
    
    void incFastEnough() {
        withinFive++;
    }
}
