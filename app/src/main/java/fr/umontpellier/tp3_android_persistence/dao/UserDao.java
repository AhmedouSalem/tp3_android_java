package fr.umontpellier.tp3_android_persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.umontpellier.tp3_android_persistence.models.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM user WHERE login = :login AND password = :password LIMIT 1")
    User getUserByLoginAndPassword(String login, String password);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    User getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM user WHERE login = :login LIMIT 1")
    User getUserByLogin(String login);

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE login = :login)")
    boolean loginExists(String login);

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE email = :email)")
    boolean emailExists(String email);

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE numTel = :numTel)")
    boolean phoneExists(String numTel);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();
}

