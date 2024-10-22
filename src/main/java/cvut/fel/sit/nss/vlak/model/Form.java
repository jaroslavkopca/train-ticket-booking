package cvut.fel.sit.nss.vlak.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cvut.fel.sit.nss.vlak.model.enums.carriageType;
import cvut.fel.sit.nss.vlak.model.enums.discountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.Date;

public class Form {
    private Station originStation;
    private Station destinationStation;
    private Date departureTime;
    private User user;
    private cvut.fel.sit.nss.vlak.model.enums.discountType userDiscountType;
    private cvut.fel.sit.nss.vlak.model.enums.carriageType carriageType = cvut.fel.sit.nss.vlak.model.enums.carriageType.SecondClass;
    private LocalDate dayOfDeparture;

    // Default constructor for JSON deserialization
    public Form() {}

    public Form(Station originStation, Station destinationStation, Date departureTime) {
        this.originStation = originStation;
        this.destinationStation = destinationStation;
        this.departureTime = departureTime;
    }

    public Station getOriginStation() {
        return originStation;
    }

    public void setOriginStation(Station originStation) {
        this.originStation = originStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public discountType getUserDiscountType() {
        return userDiscountType;
    }

    public void setUserDiscountType(discountType userDiscountType) {
        this.userDiscountType = userDiscountType;
    }

    public cvut.fel.sit.nss.vlak.model.enums.carriageType getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(cvut.fel.sit.nss.vlak.model.enums.carriageType carriageType) {
        this.carriageType = carriageType;
    }

    public LocalDate getDayOfDeparture() {
        return dayOfDeparture;
    }

    public void setDayOfDeparture(LocalDate dayOfDeparture) {
        this.dayOfDeparture = dayOfDeparture;
    }

    @Override
    public String toString() {
        return "Form{" +
                "originStation='" + originStation + '\'' +
                ", destinationStation='" + destinationStation + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", user='" + user + '\'' +
                ", userDiscountType='" + userDiscountType + '\'' +
                ", carriageType='" + carriageType + '\'' +
                ", dayOfDeparture=" + dayOfDeparture +
                '}';
    }
}
