/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cnlightsoul
 */
public class Flight {
    private String flightNo;
    private String departure;
    private String destination;
    private String takeOffTime;
    private int price;
    
    private String fullDeparture;
    private String fullDestination;
    
    public Flight(String flightNo, String departure, String destination, String takeOffTime, int price){
        this.flightNo = flightNo;
        this.departure = departure;
        this.destination = destination;
        this.takeOffTime = takeOffTime;
        this.price = price;
        
        fullDeparture = getFullLocationName(departure);
        fullDestination = getFullLocationName(destination);
        
    }
    
    public String getFlightNo(){
        return this.flightNo;
    }
    
    public String getDeparture(){
        return this.departure;
    }
    
    public String getFullLocationName(String locationCode){
        String fullLocationName = "";
        switch(locationCode){
            case "SYD":
                fullLocationName= "Sydney";
                break;
            case "MEL":
                fullLocationName = "Melbounre";
                break;
            case "CBR":
                fullLocationName = "Canberra";
                break;
            default:
                fullLocationName = "";
        }
        return fullLocationName;
    }
    
    public String getDestination(){
        return this.destination;
    }
    
    public String getFullDeparture(){
        return fullDeparture;
    }
    
    public String getFullDestination(){
        return fullDestination;
    }
    
    public String getTakeOff(){
        return this.takeOffTime;
    }
    
    public int getPrice(){
        return this.price;
    }
}
