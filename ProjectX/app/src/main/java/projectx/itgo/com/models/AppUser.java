package projectx.itgo.com.models;

/**
 * Created by Niral on 17-06-2016.
 */
public class AppUser {
    private int Id;
    private String userName;
    private String password;

    public String getPassword() {
        return password;
    }

    public AppUser(int id, String userName, String password) {
        Id = id;
        this.userName = userName;
        this.password = password;
    }

    public AppUser() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
