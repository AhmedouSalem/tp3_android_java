package fr.umontpellier.tp3_android_persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import fr.umontpellier.tp3_android_persistence.models.Planning;

@Dao
public interface PlanningDao {

    @Insert
    void insert(Planning planning);

    @Update
    void update(Planning planning);

    @Query("SELECT COUNT(*) > 0 FROM planning WHERE login = :login AND date = :date")
    boolean planningExists(String login, String date);

    @Query("SELECT * FROM planning WHERE login = :login AND date = :date LIMIT 1")
    Planning getPlanningByLoginAndDate(String login, String date);
}
