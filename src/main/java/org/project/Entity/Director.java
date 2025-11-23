package org.project.Entity;

public class Director {
    private String name;
    private String birthDate;
    private String country;

    public Director(String name, String birthDate, String country) {
        this.name = name;
        this.birthDate = birthDate;
        this.country = country;
    }
    public Director() {
        // потрібен для Jackson
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

}
