package Model;

public class FuelDetails {
    String fleet, station, amount, date, driverName;

    public FuelDetails(String fleet, String station, String amount, String date, String driverName) {
        this.fleet = fleet;
        this.station = station;
        this.amount = amount;
        this.date = date;
        this.driverName = driverName;
    }

    public String getFleet() {
        return fleet;
    }

    public String getStation() {
        return station;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDriverName() {
        return driverName;
    }
}
