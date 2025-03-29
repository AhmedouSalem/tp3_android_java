package fr.umontpellier.tp3_android_persistence.models;

import java.io.Serializable;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import java.io.Serializable;

import fr.umontpellier.tp3_android_persistence.utils.Converters;

@Entity(tableName = "user")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "numTel")
    private String numTel;

    @ColumnInfo(name = "dateNaissance")
    private String dateNaissance;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "centres_interet")
    private List<String> centresInteret;


    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNumTel() { return numTel; }
    public void setNumTel(String numTel) { this.numTel = numTel; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }


    public List<String> getCentresInteret() {
        return centresInteret;
    }

    public void setCentresInteret(List<String> centresInteret) {
        this.centresInteret = centresInteret;
    }
}
