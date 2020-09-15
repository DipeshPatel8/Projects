package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A log entry for the logfile in Asg #1
 * @author YOU!
 */
public class Log implements Comparable<Log> {

    //Constants
    public static final int IP_ADDRESS_INDEX = 0;
    public static final int SERVICE_NAME_INDEX = 1;
    public static final int DATE_INDEX = 2;
    public static final int TIME_INDEX= 3;
    public static final int LENGTH_INDEX = 4;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // Fields
    private Date timestamp;
    private IPAddress ipAddress;
    private String serviceName;
    private int length;

    // Constructors
    public Log(String logEntry) {
        if(logEntry == "")
            throw new RuntimeException("No Log" + logEntry); //empty log
        String []data = logEntry.split("\\s+");

        this.ipAddress = new IPAddress(data[IP_ADDRESS_INDEX]); //initialise IPaddress
        setServiceName(data[SERVICE_NAME_INDEX]); //set service name
        setTimestamp(data[DATE_INDEX], data[TIME_INDEX]); //since the date and time are split by a space, send both to set the timestamp
        setLength(data[LENGTH_INDEX]); //set length
    }

    // Getters and setters
    public void setTimestamp(String date, String time){
        String tmp = date+" "+time; //concatenate date and time
        try {
            this.timestamp = formatter.parse(tmp); //format and parse
        }
        catch(Exception e){
            throw new RuntimeException("Invalid Date input" + tmp); //catch error if invalid format/input
        }
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public void setServiceName(String serviceName) {
        if(serviceName == "") //throw error if service name empty
            throw new RuntimeException("No Service Name" + serviceName);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setLength(String length) {
        try{
            int tmp = Integer.parseInt(length); //parse int value
            if(tmp<0) //validate if in range
                throw new RuntimeException("Invalid Length" + length); //out of range error
            else
                this.length = tmp;
        }
        catch (Exception e){
            throw new RuntimeException("Bad length input"); //parsing error
        }
    }

    public int getLength() {
        return length;
    }

    // Methods
    @Override
    public int compareTo(Log rhs) {
        if(ipAddress.compareTo(rhs.ipAddress) == 0) { //if ip address are same check service name
            if(serviceName.compareTo(rhs.serviceName) == 0){ //if service name are same check timestamp
                if(timestamp.compareTo(rhs.timestamp) == 0) //if timestamp are same then logs are same
                        return 0;
                else
                    return timestamp.compareTo(rhs.timestamp);
            }
            else
                return serviceName.compareTo(rhs.serviceName);
        }
        else
            return ipAddress.compareTo(rhs.ipAddress);
    }

    @Override
    public String toString(){
        String log = ipAddress.toString()+" "+serviceName+" "+formatter.format(timestamp)+" "+length;
        return log; //concatenate all value and return it
    }
}
