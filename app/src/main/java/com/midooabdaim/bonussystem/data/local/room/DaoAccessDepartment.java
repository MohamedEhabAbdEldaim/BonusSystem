package com.midooabdaim.bonussystem.data.local.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccessDepartment {

    @Insert
    void add(department... nItems);

    @Update
    void update(department... nItems);


    @Delete
    void delete(department... nItems);

    @Query("SELECT * FROM department")
    List<department> getAll();

    @Query("Delete FROM department")
    void deleteALL();


}
