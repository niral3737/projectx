package projectx.itgo.com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Niral on 09-06-2016.
 */
public class Customer implements Parcelable{
    private String address;
    private Double credit;
    private Double debit;
    private String shopName;
    private String email;
    private String firstName;
    private Long id;
    private String lastName;
    private String phoneNumber;

    public Customer() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeDouble(credit);
        parcel.writeDouble(debit);
        parcel.writeString(shopName);
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeLong(id);
        parcel.writeString(lastName);
        parcel.writeString(phoneNumber);
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    private Customer(Parcel in) {
        address = in.readString();
        credit = in.readDouble();
        debit = in.readDouble();
        shopName = in.readString();
        email = in.readString();
        firstName = in.readString();
        id = in.readLong();
        lastName = in.readString();
        phoneNumber = in.readString();
    }
}
