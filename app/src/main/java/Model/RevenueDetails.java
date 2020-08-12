package Model;

public class RevenueDetails {
    String fleet, ref, amount, date, conductorName;

    public RevenueDetails(String fleet, String ref, String amount, String date, String conductorName) {
        this.fleet = fleet;
        this.ref = ref;
        this.amount = amount;
        this.date = date;
        this.conductorName = conductorName;
    }

    public String getFleet() {
        return fleet;
    }

    public String getRef() {
        return ref;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getConductorName() {
        return conductorName;
    }
}
