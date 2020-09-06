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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
