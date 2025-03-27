package fr.umontpellier.tp3_android_persistence.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.umontpellier.tp3_android_persistence.database.UserDatabaseHelper;
import fr.umontpellier.tp3_android_persistence.models.Planning;

public class PlanningDAO {
    private final SQLiteDatabase db;

    public PlanningDAO(Context context) {
        UserDatabaseHelper helper = new UserDatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertPlanning(Planning planning) {
        ContentValues values = new ContentValues();
        values.put("login", planning.getLoginUser());
        values.put("slot1", planning.getSlot1());
        values.put("slot2", planning.getSlot2());
        values.put("slot3", planning.getSlot3());
        values.put("slot4", planning.getSlot4());
        values.put("date", planning.getDate());

        return db.insert("planning", null, values);
    }

    public boolean planningExists(String login, String date) {
        Cursor cursor = db.query("planning", null, "login = ?  AND date=?", new String[]{login, date}, null, null, null);
        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        return exists;
    }

    public int updatePlanning(Planning planning) {
        ContentValues values = new ContentValues();
        values.put("slot1", planning.getSlot1());
        values.put("slot2", planning.getSlot2());
        values.put("slot3", planning.getSlot3());
        values.put("slot4", planning.getSlot4());

        return db.update("planning", values, "login=? AND date=?", new String[]{planning.getLoginUser(), planning.getDate()});
    }

    public Planning getPlanningByLogin(String login, String date) {
        Cursor cursor = db.query("planning", null, "login=? AND date=?", new String[]{login, date}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Planning p = new Planning();
            p.setLoginUser(cursor.getString(cursor.getColumnIndexOrThrow("login")));
            p.setSlot1(cursor.getString(cursor.getColumnIndexOrThrow("slot1")));
            p.setSlot2(cursor.getString(cursor.getColumnIndexOrThrow("slot2")));
            p.setSlot3(cursor.getString(cursor.getColumnIndexOrThrow("slot3")));
            p.setSlot4(cursor.getString(cursor.getColumnIndexOrThrow("slot4")));
            cursor.close();
            return p;
        }
        if (cursor != null) cursor.close();
        return null;
    }
}
