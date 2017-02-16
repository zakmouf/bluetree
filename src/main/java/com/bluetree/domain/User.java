package com.bluetree.domain;

import com.bluetree.util.MessageUtil;

public class User extends Persistable implements Comparable<User> {

    private static final long serialVersionUID = 1L;

    private String login;

    private String password;

    private String name;

    private String company;

    private String email;

    private String phone;

    private String address;

    private String zip;

    private String city;

    private String country;

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getCompany() {
	return company;
    }

    public void setCompany(String company) {
	this.company = company;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    @Override
    public int compareTo(User other) {
	return login.compareTo(other.getLogin());
    }

    @Override
    public String toString() {
	return MessageUtil.msg("[{0},{1}]", getId(), login);
    }

}
