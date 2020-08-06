package Model;

public class StaffItem {

    private String profilePic, name, staff_id, role, mobile, email, tax_pin, national_id,
            license, insuranceStatus, insurancePolicy, bloodGroup,
            medicalCondition;

    public StaffItem(String profilePic, String name, String staff_id, String role, String mobile, String email,
                     String tax_pin, String national_id, String license, String insuranceStatus,
                     String insurancePolicy, String bloodGroup, String medicalCondition) {

        this.profilePic = profilePic;
        this.name = name;
        this.staff_id = staff_id;
        this.role = role;
        this.mobile = mobile;
        this.email = email;
        this.tax_pin = tax_pin;
        this.national_id = national_id;
        this.license = license;
        this.insuranceStatus = insuranceStatus;
        this.insurancePolicy = insurancePolicy;
        this.bloodGroup = bloodGroup;
        this.medicalCondition = medicalCondition;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getName() {
        return name;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public String getRole() {
        return role;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getTax_pin() {
        return tax_pin;
    }

    public String getNational_id() {
        return national_id;
    }

    public String getLicense() {
        return license;
    }

    public String getInsuranceStatus() {
        return insuranceStatus;
    }

    public String getInsurancePolicy() {
        return insurancePolicy;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }


}
