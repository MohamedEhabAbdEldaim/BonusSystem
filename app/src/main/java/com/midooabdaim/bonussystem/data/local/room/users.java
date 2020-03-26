package com.midooabdaim.bonussystem.data.local.room;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = department.class,
        parentColumns = "departmentName"
        , childColumns = "department_Name"
        , onDelete = CASCADE))
public class users {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private double userValue;
    private String department_Name;

    public users() {
    }

    @Ignore
    public users(String userName, double userValue, String departmentName) {
        this.userName = userName;
        this.userValue = userValue;
        this.department_Name = departmentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserValue() {
        return userValue;
    }

    public void setUserValue(double userValue) {
        this.userValue = userValue;
    }

    public String getDepartment_Name() {
        return department_Name;
    }

    public void setDepartment_Name(String department_Name) {
        this.department_Name = department_Name;
    }
}
