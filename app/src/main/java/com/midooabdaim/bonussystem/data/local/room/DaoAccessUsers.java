package com.midooabdaim.bonussystem.data.local.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccessUsers {
    @Insert
    void add(users... nUsers);

    @Update
    void update(users... nUsers);

    @Delete
    void delete(users... nUsers);

    @Query("select * from users where department_Name=:department")
    List<users> getAll(String department);

    @Query("Delete FROM users where department_Name=:department")
    void deleteAll(String department);

}
