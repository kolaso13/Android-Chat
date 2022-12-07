package com.example.androidchat.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MensajeDAO {
    @Query("SELECT * FROM Mensaje")
    LiveData<List<Mensaje>> getAll();

    @Insert
    void insert(Mensaje mensaje);

}
