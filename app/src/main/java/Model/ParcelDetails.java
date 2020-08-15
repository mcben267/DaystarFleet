package Model;

public class ParcelDetails {
    String parcel_id, receiver_name, receiver_mobile, receiver_address,
            parcel_category, parcel_status, parcel_destination, parcel_origin, parcel_image;

    public ParcelDetails(String parcel_id, String receiver_name, String receiver_mobile,
                         String receiver_address, String parcel_category, String parcel_status,
                         String parcel_destination, String parcel_origin, String parcel_image) {

        this.parcel_id = parcel_id;
        this.receiver_name = receiver_name;
        this.receiver_mobile = receiver_mobile;
        this.receiver_address = receiver_address;
        this.parcel_category = parcel_category;
        this.parcel_status = parcel_status;
        this.parcel_destination = parcel_destination;
        this.parcel_origin = parcel_origin;
        this.parcel_image = parcel_image;
    }

    public String getParcel_id() {
        return parcel_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public String getParcel_category() {
        return parcel_category;
    }

    public String getParcel_status() {
        return parcel_status;
    }

    public String getParcel_destination() {
        return parcel_destination;
    }

    public String getParcel_origin() {
        return parcel_origin;
    }

    public String getParcel_image() {
        return parcel_image;
    }
}
