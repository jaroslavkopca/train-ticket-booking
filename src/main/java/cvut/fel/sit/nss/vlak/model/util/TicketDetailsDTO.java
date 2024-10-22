package cvut.fel.sit.nss.vlak.model.util;

import java.util.Date;
import cvut.fel.sit.nss.vlak.model.enums.ticketStatus;

public class TicketDetailsDTO {
    private Date date;
    private Integer price;
    private ticketStatus ticketStatus;
    private String username;
    private Integer seatNumber;
    private Integer carriageNumber;
    private String originStationName;
    private String destinationStationName;
    private Date arrivalTime;
    private Date departureTime;

    public TicketDetailsDTO() {}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public cvut.fel.sit.nss.vlak.model.enums.ticketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(cvut.fel.sit.nss.vlak.model.enums.ticketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getCarriageNumber() {
        return carriageNumber;
    }

    public void setCarriageNumber(Integer carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public String getOriginStationName() {
        return originStationName;
    }

    public void setOriginStationName(String originStationName) {
        this.originStationName = originStationName;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public void setDestinationStationName(String destinationStationName) {
        this.destinationStationName = destinationStationName;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "TicketDetailsDTO{" +
                "date=" + date +
                ", price=" + price +
                ", ticketStatus=" + ticketStatus +
                ", username='" + username + '\'' +
                ", seatNumber=" + seatNumber +
                ", carriageNumber=" + carriageNumber +
                ", originStationName='" + originStationName + '\'' +
                ", destinationStationName='" + destinationStationName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                '}';
    }


}
