package Model;

public class FleetDetails {

    String fleetId, image, type, capacity, regNum, commissionDate, chaise_no, mileage;

    public FleetDetails(String fleetId, String image, String type, String capacity, String regNum,
                        String commissionDate, String chaise_no, String mileage) {
        this.fleetId = fleetId;
        this.image = image;
        this.type = type;
        this.capacity = capacity;
        this.regNum = regNum;
        this.commissionDate = commissionDate;
        this.chaise_no = chaise_no;
        this.mileage = mileage;
    }

    public String getFleetId() {
        return fleetId;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getRegNum() {
        return regNum;
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public String getChaise_no() {
        return chaise_no;
    }

    public String getMileage() {
        return mileage;
    }
}
