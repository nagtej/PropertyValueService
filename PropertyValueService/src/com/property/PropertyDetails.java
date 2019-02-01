package com.property;

public class PropertyDetails {
	
	private Address address;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getHome_id() {
		return home_id;
	}
	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	private String home_id;
	private String owner;
	private int value;

}
