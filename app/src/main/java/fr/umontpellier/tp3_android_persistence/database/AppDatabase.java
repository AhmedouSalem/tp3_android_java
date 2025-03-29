package fr.umontpellier.tp3_android_persistence.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import fr.umontpellier.tp3_android_persistence.dao.UserDao;
import fr.umontpellier.tp3_android_persistence.models.User;
import fr.umontpellier.tp3_android_persistence.utils.Converters;

import fr.umontpellier.tp3_android_persistence.dao.PlanningDao;
import fr.umontpellier.tp3_android_persistence.models.Planning;

@Database(entities = {User.class, Planning.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract PlanningDao planningDao(); // ✅ Ajout du DAO Planning

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "user_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
