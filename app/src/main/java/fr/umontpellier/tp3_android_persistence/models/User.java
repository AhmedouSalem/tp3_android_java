package fr.umontpellier.tp3_android_persistence.models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String login, password, nom, prenom, dateNaissance, numTel, email;
    private List<String> centresInteret;

    public User() {
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCentresInteret() {
        return centresInteret;
    }

    public void setCentresInteret(List<String> centresInteret) {
        this.centresInteret = centresInteret;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", numTel='" + numTel + '\'' +
                ", email='" + email + '\'' +
                ", centresInteret=" + centresInteret +
                '}';
    }
}
