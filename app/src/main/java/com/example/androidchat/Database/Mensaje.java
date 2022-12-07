package com.example.androidchat.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mensaje {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "texto")
    public String texto;

    @ColumnInfo(name = "usuario")
    public String usuario;
}
