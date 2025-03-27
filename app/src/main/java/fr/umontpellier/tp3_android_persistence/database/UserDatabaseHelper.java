package fr.umontpellier.tp3_android_persistence.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tp3_users.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user";

    public static final String COL_ID = "id";
    public static final String COL_LOGIN = "login";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NOM = "nom";
    public static final String COL_PRENOM = "prenom";
    public static final String COL_DATE_NAISSANCE = "date_naissance";
    public static final String COL_TEL = "tel";
    public static final String COL_EMAIL = "email";
    public static final String COL_INTERETS = "interets";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_LOGIN + " TEXT UNIQUE, " +
                    COL_PASSWORD + " TEXT, " +
                    COL_NOM + " TEXT, " +
                    COL_PRENOM + " TEXT, " +
                    COL_DATE_NAISSANCE + " TEXT, " +
                    COL_TEL + " TEXT, " +
                    COL_EMAIL + " TEXT, " +
                    COL_INTERETS + " TEXT);";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL("CREATE TABLE planning (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "login TEXT," +
                "date TEXT," +
                "slot1 TEXT," +
                "slot2 TEXT," +
                "slot3 TEXT," +
                "slot4 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
