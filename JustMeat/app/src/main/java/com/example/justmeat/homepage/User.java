package com.example.justmeat.homepage;

public class User {
    private int id;
    private String name,last_name,mail;

    public User(int id,String name, String last_name, String mail) {
        this.id=id;
        this.name = name;
        this.last_name = last_name;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getMail() {
        return mail;
    }
}
