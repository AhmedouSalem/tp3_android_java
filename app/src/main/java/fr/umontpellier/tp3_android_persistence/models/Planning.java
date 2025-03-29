package fr.umontpellier.tp3_android_persistence.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.io.Serializable;

@Entity(tableName = "planning")
public class Planning implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "login")
    private String loginUser;

    @ColumnInfo(name = "slot1")
    private String slot1;

    @ColumnInfo(name = "slot2")
    private String slot2;

    @ColumnInfo(name = "slot3")
    private String slot3;

    @ColumnInfo(name = "slot4")
    private String slot4;

    @ColumnInfo(name = "date")
    private String date;

    public Planning() {}

    public Planning(String loginUser, String slot1, String slot2, String slot3, String slot4, String date) {
        this.loginUser = loginUser;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        this.slot4 = slot4;
        this.date = date;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getLoginUser() { return loginUser; }
    public void setLoginUser(String loginUser) { this.loginUser = loginUser; }

    public String getSlot1() { return slot1; }
    public void setSlot1(String slot1) { this.slot1 = slot1; }

    public String getSlot2() { return slot2; }
    public void setSlot2(String slot2) { this.slot2 = slot2; }

    public String getSlot3() { return slot3; }
    public void setSlot3(String slot3) { this.slot3 = slot3; }

    public String getSlot4() { return slot4; }
    public void setSlot4(String slot4) { this.slot4 = slot4; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
