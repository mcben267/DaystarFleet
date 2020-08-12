package Model;

public class SubContractDetails {

    String contract_id, company, contact_person, mobile, contract_details,
            contract_duration, contract_expire_date;

    public SubContractDetails(String contract_id, String company, String contact_person, String mobile,
                              String contract_details, String contract_duration, String contract_expire_date) {
        this.contract_id = contract_id;
        this.company = company;
        this.contact_person = contact_person;
        this.mobile = mobile;
        this.contract_details = contract_details;
        this.contract_duration = contract_duration;
        this.contract_expire_date = contract_expire_date;
    }

    public String getContract_id() {
        return contract_id;
    }

    public String getCompany() {
        return company;
    }

    public String getContact_person() {
        return contact_person;
    }

    public String getMobile() {
        return mobile;
    }

    public String getContract_details() {
        return contract_details;
    }

    public String getContract_duration() {
        return contract_duration;
    }

    public String getContract_expire_date() {
        return contract_expire_date;
    }

}
