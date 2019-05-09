package com.example.bookkar;

public class User {
    private Address address;
    private String connection_type;
    private String consumer_no;
    private String email;
    private String first_name;
    private String gender;
    private String last_name;
    private String middle_name;
    private String phone_no;
    private String user_id;


    public User(){

    }

    public User(Address address,String connection_type, String consumer_no, String email ,String first_name,  String gender, String last_name ,String middle_name,String phone_no, String user_id) {

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