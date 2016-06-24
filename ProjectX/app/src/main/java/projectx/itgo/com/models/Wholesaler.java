package projectx.itgo.com.models;

/**
 * Created by Niral on 21-06-2016.
 */
public class Wholesaler {

    private String address;
    private Double credit;
    private Double debit;
    private String shopName;
    private String email;
    private String firstName;
    private Integer id;
    private String lastName;
    private String phoneNumber;

    public Wholesaler() {
    }

    public Wholesaler(String address, Double credit, Double debit, String shopName, String email, String firstName, Integer id, String lastName, String phoneNumber) {
        this.address = address;
        this.credit = credit;
        this.debit = debit;
        this.shopName = shopName;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
