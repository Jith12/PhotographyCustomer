package com.example.photographycustomer.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SelectPhoto.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
    public abstract SelectPhotoDAO selectPhotoDAO();
}
