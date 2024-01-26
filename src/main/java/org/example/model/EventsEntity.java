package org.example.model;

public class EventsEntity {
    private int id;

    private String name;

    private String city;

    public EventsEntity(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public EventsEntity() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
