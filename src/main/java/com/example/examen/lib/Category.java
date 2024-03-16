package com.example.examen.lib;

public class Category {
    private int id;
    private String libelle;

    public Category(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Category(String libelle) {
        this.libelle = libelle;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
