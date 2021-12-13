package ca.qc.bdeb.inf203.shared;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {
    private String nom;
    private final int id;

    public User(String nom, int id) {
        this.nom = nom;
        this.id = id;
    }

    public User(String nom, String id) {
        this.nom = nom;

        int tmpid;
        try {
            tmpid = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            tmpid = genID();
        }

        this.id = tmpid;
    }

    public User(String nom) {
        this.nom = nom;

        id = genID();
    }


    private static int genID() {
        Random rnd = new Random();

        return rnd.nextInt();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }
}
