package projectx.itgo.com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Niral on 09-06-2016.
 */
public class Customer implements Parcelable{
    private String address;
    private Double balance;
    private String companyName;
    private String email;
    private String firstName;
    private Integer id;
    private String lastName;
    private String phoneNumber;

    public Customer() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeDouble(balance);
        parcel.writeString(companyName);
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeInt(id);
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
        balance = in.readDouble();
        companyName = in.readString();
        email = in.readString();
        firstName = in.readString();
        id = in.readInt();
        lastName = in.readString();
        phoneNumber = in.readString();
    }
}
