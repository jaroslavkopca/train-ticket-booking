package cvut.fel.sit.nss.vlak.model.util;

import cvut.fel.sit.nss.vlak.model.enums.carriageType;
import cvut.fel.sit.nss.vlak.model.enums.seatType;

public class SeatDetailsDTO {
    private Integer number;
    private seatType seatType;
    private Integer carriageNumber;
    private carriageType carriageType;
    private Integer trainType;
    private Integer connectionNumber;
    private boolean available;


    public SeatDetailsDTO() {
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public cvut.fel.sit.nss.vlak.model.enums.seatType getSeatType() {
        return seatType;
    }

    public void setSeatType(cvut.fel.sit.nss.vlak.model.enums.seatType seatType) {
        this.seatType = seatType;
    }

    public Integer getCarriageNumber() {
        return carriageNumber;
    }

    public void setCarriageNumber(Integer carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public cvut.fel.sit.nss.vlak.model.enums.carriageType getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(cvut.fel.sit.nss.vlak.model.enums.carriageType carriageType) {
        this.carriageType = carriageType;
    }

    public Integer getTrainType() {
        return trainType;
    }

    public void setTrainType(Integer trainType) {
        this.trainType = trainType;
    }

    public Integer getConnectionNumber() {
        return connectionNumber;
    }

    public void setConnectionNumber(Integer connectionNumber) {
        this.connectionNumber = connectionNumber;
    }
}
