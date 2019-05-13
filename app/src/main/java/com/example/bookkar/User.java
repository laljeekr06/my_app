package com.example.bookkar;

public class User {
    private Address address;
    private BankDetails bank;
    private Nominee nominee;
    private String connection_type;
    private String consumer_no;
    private String email;
    private String first_name;
    private String aadhar;
    private String profession;
    private String Citizen;
    private String gender;
    private String last_name;
    private String middle_name;
    private String phone_no;
    private String user_id;
    private String dob;

    public User(){

    }


    public User(Address address,String dob,String aadhar,String profession,String citizen, String connection_type, String consumer_no, String email , String first_name, String gender, String last_name , String middle_name, String phone_no, String user_id, Nominee nominee, BankDetails bank) {

        this.user_id = user_id;
        this.phone_no = phone_no;
        this.email = email;
        this.consumer_no = consumer_no;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.address = address;
        this.connection_type = connection_type;
        this.gender = gender;
        this.bank = bank;
        this.nominee = nominee;
        this.aadhar = aadhar;
        this.profession = profession;
        this.Citizen = citizen;
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCitizen() {
        return Citizen;
    }

    public void setCitizen(String citizen) {
        Citizen = citizen;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsumer_no() {
        return consumer_no;
    }

    public void setConsumer_no(String consumer_no) {
        this.consumer_no = consumer_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public BankDetails getBank() {
        return bank;
    }

    public void setBank(BankDetails bank) {
        this.bank = bank;
    }

    public Nominee getNominee() {
        return nominee;
    }

    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }
    public String getConnection_type() {
        return connection_type;
    }

    public void setConnection_type(String connection_type) {
        this.connection_type = connection_type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

class Address{
    private String city;
    private String flat_no;
    private String pincode;
    private String state;
    private String street_name;

    public Address(){

    }

    public Address(String city,String flat_no, String pincode, String state, String street_name) {
        this.flat_no = flat_no;
        this.street_name = street_name;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}

class Nominee{
     String fname;
     String lname;
     String age;
     String gender;
     String relation;

     public Nominee(){

     }

     public Nominee(String fname,String lname,String age,String gender,String relation){
         this.fname = fname;
         this.lname = lname;
         this.age = age;
         this.gender = gender;
         this.relation = relation;
     }

    public String getAge() {
        return age;
    }

    public String getFname() {
        return fname;
    }

    public String getGender() {
        return gender;
    }

    public String getLname() {
        return lname;
    }

    public String getRelation() {
        return relation;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

}

class BankDetails{
    String accountNo;
    String bankName;
    String bankBranch;
    String ifsc_code;
    String panCard;

    public BankDetails(){

    }

    public BankDetails(String acc,String bank,String branch,String ifsc,String pan){
            this.accountNo = acc;
            this.bankBranch = branch;
            this.bankName = bank;
            this.ifsc_code = ifsc;
            this.panCard = pan;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getIFSC_code() {
        return ifsc_code;
    }

    public void setIFSC_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }
}