package cvut.fel.sit.nss.vlak.model.util;
import cvut.fel.sit.nss.vlak.model.enums.carriageType;

import java.util.Date;

public class ConnectionDetailsDTO {
    private String originStation;
    private String destinationStation;
    private Date departureDateTime;
    private Date arrivalDateTime;
    private int ticketPrice;
    private carriageType carriageType;
    private String departureDay; // Added field for day of departure

    // Getters and Setters
    public String getOriginStation() {
        return originStation;
    }

    public void setOriginStation(String originStation) {
        this.originStation = originStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(Date departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(Date arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public cvut.fel.sit.nss.vlak.model.enums.carriageType getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(cvut.fel.sit.nss.vlak.model.enums.carriageType carriageType) {
        this.carriageType = carriageType;
    }

    public String getDepartureDay() {
        return departureDay;
    }

    public void setDepartureDay(String departureDay) {
        this.departureDay = departureDay;
    }
}
