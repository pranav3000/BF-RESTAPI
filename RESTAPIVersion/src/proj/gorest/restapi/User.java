package proj.gorest.restapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Comparable<User>{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", gender=" + gender + ", status=" + status
                + "]";
    }

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public int compareTo(User otherUser) {
        return name.compareTo(otherUser.name);
    }

}