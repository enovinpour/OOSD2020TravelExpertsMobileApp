package Model;

import java.util.Date;

public class BookingDetail {
    private int itineraryNo;
    private String destination;
    private Date tripStart;
    private Date tripEnd;
    private String description;
    private double basePrice;
    private int bookingId;

    public BookingDetail(int itineraryNo, String destination, Date tripStart, Date tripEnd, String description, double basePrice, int bookingId) {
        this.itineraryNo = itineraryNo;
        this.destination = destination;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.description = description;
        this.basePrice = basePrice;
        this.bookingId = bookingId;
    }

    public int getItineraryNo() {
        return itineraryNo;
    }

    public void setItineraryNo(int itineraryNo) {
        this.itineraryNo = itineraryNo;
    }

    public Date getTripStart() {
        return tripStart;
    }

    public void setTripStart(Date tripStart) {
        this.tripStart = tripStart;
    }

    public Date getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(Date tripEnd) {
        this.tripEnd = tripEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
