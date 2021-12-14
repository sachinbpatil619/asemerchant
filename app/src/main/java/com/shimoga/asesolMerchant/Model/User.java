package com.shimoga.asesolMerchant.Model;

public class User {
    private String Name;
    private String Password;
    private String Email;
    private String Phone;
    private String IsStaff;

    public User() {
    }


    public User(String name, String password, String email, String phone, String isStaff) {
        Name = name;
        Password = password;
        Email = email;
        Phone = phone;
        IsStaff = isStaff;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getPhone() { return Phone; }

    public void setPhone(String phone) { Phone = phone; }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
