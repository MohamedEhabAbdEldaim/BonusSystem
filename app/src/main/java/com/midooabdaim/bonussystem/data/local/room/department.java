package com.midooabdaim.bonussystem.data.local.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class department {

    @PrimaryKey
    @NonNull
    private String departmentName;
    private Float departmentDegree;
    private Boolean dependsOn;


    public department() {
    }

    @Ignore
    public department(String name, Float departmentDegree, Boolean dependsOn) {
        this.departmentName = name;
        this.departmentDegree = departmentDegree;
        this.dependsOn = dependsOn;

    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Float getDepartmentDegree() {
        return departmentDegree;
    }

    public void setDepartmentDegree(Float departmentDegree) {
        this.departmentDegree = departmentDegree;
    }

    public Boolean getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(Boolean dependsOn) {
        this.dependsOn = dependsOn;
    }
}
