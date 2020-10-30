/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cnlightsoul
 */
public class Customer {
    private String userName;
    
    ArrayList<Trip> pastTrips = new ArrayList<>();
    ArrayList<Trip> ongoingTrips = new ArrayList<>();
    ArrayList<Trip> futureTrips = new ArrayList<>();
    
    Trip tempTrip = null;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public Customer(String userName) throws FileNotFoundException, IOException, ParseException{
        this.userName = userName;
        String fileName = userName + ".txt";
        File f = new File(fileName);
        if(f.exists() && !f.isDirectory()){
            Scanner reader = new Scanner(f);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                if(line.equals("")) continue;
                //System.out.println("Found travel: " + line);
                String[] splittedLine = line.split(",");
                //flightNo,date,seat,(optional)services
                Trip p;
                if(splittedLine.length == 3){
                    p = new Trip(userName,splittedLine[0],splittedLine[2],splittedLine[1],"");
                }
                else{
                    p = new Trip(userName,splittedLine[0],splittedLine[2],splittedLine[1],splittedLine[3]);
                }
                Date tripDate = sdf.parse(splittedLine[1]);
                Date now = sdf.parse(sdf.format(new Date()));
                if(tripDate.before(now)){
                    pastTrips.add(p);
                }
                else if (tripDate.after(now)){
                    futureTrips.add(p);
                }
                else{
                    ongoingTrips.add(p);
                }
            }
        }
        else{
            f.createNewFile();
        }
        
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void refresh(){
        //sync the local data to txt file
        String fileName = userName + ".txt";
        try {
            FileWriter w = new FileWriter(fileName);
            w.write("");
            System.out.println("Old records cleard;");
            w.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
            for(Trip p: pastTrips){
                String s = p.getFlightNo() + ","+p.getDepartureDate()+ "," + p.getSeatNo()+",";
                if(!p.checkServices().isEmpty()){
                    String app = "";
                    for(String t: p.checkServices()){
                        app = app + t;
                        if(p.checkServices().indexOf(t) != p.checkServices().size()-1){
                            app = app + ";";
                        }
                    }
                    s = s+app;
                }
                bw.append(s);
                bw.append("\n");
                
            }
            for(Trip p: ongoingTrips){
                String s = p.getFlightNo() + ","+p.getDepartureDate()+ "," + p.getSeatNo()+",";
                if(!p.checkServices().isEmpty()){
                    String app = "";
                    for(String t: p.checkServices()){
                        app = app + t;
                        if(p.checkServices().indexOf(t) != p.checkServices().size()-1){
                            app = app + ";";
                        }
                    }
                    s = s+app;
                }
                bw.append(s);
                bw.append("\n");
                
            }
            for(Trip p: futureTrips){
                String s = p.getFlightNo() + ","+p.getDepartureDate()+ "," + p.getSeatNo()+",";
                if(!p.checkServices().isEmpty()){
                    String app = "";
                    for(String t: p.checkServices()){
                        app = app + t;
                        if(p.checkServices().indexOf(t) != p.checkServices().size()-1){
                            app = app + ";";
                        }
                    }
                    s = s+app;
                }
                bw.append(s);
                bw.append("\n");
                
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
    public void addTrip(Trip p){
        futureTrips.add(p);
        refresh();
    }
    
    public void cancelTrip(String flightNo, String date){
        for (Trip p: futureTrips){
            if(p.getFlightNo().equals(flightNo) && p.getDepartureDate().equals(date)){
                futureTrips.remove(p);
            }
        }
        refresh();
    }
    
    public ArrayList<Trip> getPastTrips(){
        return pastTrips;
    }
    
    public ArrayList<Trip> getOngoingTrips(){
        return ongoingTrips;
    }
    
    public ArrayList<Trip> getFutureTrips(){
        return futureTrips;
    }

    public void setTempTrip(String flightNo, String departureDate){
        tempTrip = new Trip(userName, flightNo,"",departureDate,"");
        System.out.println("temporary trip set!");
    }
    
    public void setTempTripSeat(String seatNo){
        tempTrip.setSeatNo(seatNo);
        System.out.println("temporary trip seat selected!");
    }
    
    public void pushTempTrip(){
        futureTrips.add(tempTrip);
        System.out.println("temporary trip pushed to file!");
        refresh();
        tempTrip = null;
    }
    
    public void discardTempTrip(){
        tempTrip = null;
        System.out.println("temporary trip discarded!");
    }
    
    public Trip getTempTrip(){
        return tempTrip;
    }
    
    public Trip getSingleFutureTrip(String flightNo, String flightDate){
        for(Trip t: futureTrips){
            if(t.getFlightNo().equals(flightNo) && t.getDepartureDate().equals(flightDate)){
                return t;
            }
        }
        return null;
    }
    
    public void addService(String flightNo, String date, String serviceNo){
        System.out.println("Added service is: " + serviceNo);
        for(Trip t: futureTrips){
            if(t.getFlightNo().equals(flightNo) && t.getDepartureDate().equals(date)){
                t.addService(serviceNo);
                break;
            }
        }
        refresh();
    }
    
    public void removeService(String flightNo, String date, String serviceNo){
        for(Trip t: futureTrips){
            if(t.getFlightNo().equals(flightNo) && t.getDepartureDate().equals(date)){
                t.removeService(serviceNo);
                break;
            }
        }
        refresh();
    }
    
    public void changeSeatNo(String flightNo, String date, String seatNo){
        for(Trip t: futureTrips){
             if(t.getFlightNo().equals(flightNo) && t.getDepartureDate().equals(date)){
                 t.setSeatNo(seatNo);
                 break;
             }
        }
        refresh();
    }
    
    public void cancelFlight(String flightNo, String date){
        for(Trip t: futureTrips){
            if(t.getFlightNo().equals(flightNo) && t.getDepartureDate().equals(date)){
                futureTrips.remove(t);
                break;
            }
        }
        refresh();
    }
    
}
