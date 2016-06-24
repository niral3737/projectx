package projectx.itgo.com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Niral on 24-06-2016.
 */
public class Product implements Parcelable {

    private Long id;
    private String name;
    private String model;
    private String company;
    private String type;
    private String code;
    private Integer quantity;
    private Double price;

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(model);
        parcel.writeString(company);
        parcel.writeString(type);
        parcel.writeString(code);
        parcel.writeInt(quantity);
        parcel.writeDouble(price);
    }
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        model = in.readString();
        company = in.readString();
        type = in.readString();
        code = in.readString();
        quantity = in.readInt();
        price = in.readDouble();

    }

    public Product() {
    }

    public Product(Long id, String name, String model, String company, String type, String code, Integer quantity, Double price) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.company = company;
        this.type = type;
        this.code = code;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
