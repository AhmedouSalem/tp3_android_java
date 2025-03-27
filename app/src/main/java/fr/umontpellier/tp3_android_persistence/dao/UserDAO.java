package fr.umontpellier.tp3_android_persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;

import fr.umontpellier.tp3_android_persistence.database.UserDatabaseHelper;
import fr.umontpellier.tp3_android_persistence.models.User;

public class UserDAO {

    private final SQLiteDatabase db;

    public UserDAO(Context context) {
        UserDatabaseHelper helper = new UserDatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public boolean insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("login", user.getLogin());
        values.put("password", user.getPassword());
        values.put("nom", user.getNom());
        values.put("prenom", user.getPrenom());
        values.put("date_naissance", user.getDateNaissance());
        values.put("tel", user.getNumTel());
        values.put("email", user.getEmail());
        values.put("interets", String.join(",", user.getCentresInteret()));
        return db.insert("user", null, values) != -1;
    }

    public boolean loginExists(String login) {
        Cursor cursor = db.query("user", new String[]{"id"}, "login=?", new String[]{login}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean phoneExists(String phone) {
        Cursor cursor = db.query("user", new String[]{"id"}, "tel=?", new String[]{phone}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean emailExists(String email) {
        Cursor cursor = db.query("user", new String[]{"id"}, "email=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public User getUser(String login, String password) {
        Cursor cursor = db.query(
                "user",
                null,
                "(login = ? OR email = ?) AND password = ?",
                new String[]{login, login, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setLogin(cursor.getString(cursor.getColumnIndexOrThrow("login")));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            user.setNom(cursor.getString(cursor.getColumnIndexOrThrow("nom")));
            user.setPrenom(cursor.getString(cursor.getColumnIndexOrThrow("prenom")));
            user.setDateNaissance(cursor.getString(cursor.getColumnIndexOrThrow("date_naissance")));
            user.setNumTel(cursor.getString(cursor.getColumnIndexOrThrow("tel")));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            String interets = cursor.getString(cursor.getColumnIndexOrThrow("interets"));
            user.setCentresInteret(Arrays.asList(interets.split(",")));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }
}
