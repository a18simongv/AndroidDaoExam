package com.example.daoandroid.database.models;

public class TypeMultimedia {

    //id autoincrement
    private int id;

    //type of multimedia
    private String type;
    //uri multimedia
    private String path;

    public TypeMultimedia() {}
    public TypeMultimedia(int id, String type, String path) {
        this.id = id;
        this.type = type;
        this.path = path;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

}
