package programminglab7;

public class Customer {

    private String name;
    private String phone;
    private Address address;

    public Customer(String name, String phone, String street, String district, String city) {
        this.name = name;
        this.phone = phone;
        this.address = new Address(street, district, city);
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer:" + name + ")" + phone + ")Address:" + address;
    }
}
