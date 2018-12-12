package com.example.greeting.MyObject;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "phone")
public class Phone  {
    @Id
    public long id;
    public String name;
    public DetailPhone detailPhone;


    public Phone() {

    }

    public Phone(long id, String name, DetailPhone detailPhone) {
        this.id = id;
        this.name = name;
        this.detailPhone = detailPhone;
    }

    public Phone(long id, String name, String color, String os){
        this.id = id;
        this.name = name;
        this.detailPhone = new DetailPhone(color,os);
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DetailPhone getDetailPhone() {
        return detailPhone;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetailPhone(String color, String os) {
        this.detailPhone.color = color;
        this.detailPhone.os = os;
    }

    public void setDetailPhone(DetailPhone detailPhone) {
        this.detailPhone = detailPhone;
    }

    public static class DetailPhone{
        public String color;
        public String os;

        public DetailPhone() {}

        public DetailPhone (String color, String os) {
            this.color = color;
            this.os = os;
        }

        public String getColor() {
            return color;
        }

        public String getOs() {
            return os;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setOs(String os) {
            this.os = os;
        }
    }

}

