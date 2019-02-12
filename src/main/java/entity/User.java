package entity;

import java.util.Date;

public class User {
    private String username;
    private Date birthday;
    private String email;

    public User() {
    }

    public User(String username, Date birthday, String email) {
        this.username = username;
        this.birthday = birthday;
        this.email = email;
    }

    public void printUserCredentials(User user){
        System.out.printf("entity.User %s was born %s and has email address %s", user.getUsername(), user.getBirthday(), user.getEmail());
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
