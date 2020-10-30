/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date;
import java.util.ArrayList;
/**
 *
 * @author cnlightsoul
 */
public class Trip {
    private String userName;
    private String flightNo;
    private String seatNo;
    private String departureDate;
    private String takeOffTime;
    private ArrayList<String> serviceList = new ArrayList<>();
    
    public Trip(String userName, String flightNo, String seatNo, String departureDate, String services){
        this.userName = userName;
        this.flightNo = flightNo;
        this.seatNo = seatNo;
        this.departureDate = departureDate;
        String[] splittedServices = services.split(";");
        if(splittedServices.length != 0){
            for(String s: splittedServices){
                
            }
        }
    }
    
    public String getUserName(){
        return userName;
    }
    
    public String getFlightNo(){
        return flightNo;
    }
    
    public String getSeatNo(){
        return seatNo;
    }
    
    public String getDepartureDate(){
        return departureDate;
    }
    
    public void setSeatNo(String seatNo){
        this.seatNo = seatNo;
    }
    
    public void addService(String serviceNo){
        serviceList.add(serviceNo);
    }
    
    public void removeService(String serviceNo){
        if(serviceList.contains(serviceNo)){
            serviceList.remove(serviceNo);
        }
    };
    
    public ArrayList<String> checkServices(){
        return serviceList;
    }
}
