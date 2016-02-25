package com.se.findmyphone;

public class ContactList {
    private String name;
    private String no;

    public ContactList(String name, String no) {
        this.name = name;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setName(String name) {

        this.name = name;
    }
}