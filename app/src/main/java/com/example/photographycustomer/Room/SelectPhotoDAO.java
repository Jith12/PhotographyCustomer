package com.example.photographycustomer.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SelectPhotoDAO {

    @Query("SELECT * FROM selectphoto")
    List<SelectPhoto> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SelectPhoto selectphoto);

    @Delete
    void delete(SelectPhoto selectphoto);

    @Query("DELETE FROM selectphoto")
    void clear();

    @Query("DELETE FROM selectphoto WHERE selected_images = :filename")
    void removeByFilename(String filename);

    @Query("SELECT * FROM selectphoto WHERE selected_images = :filename")
    List<SelectPhoto> getPhoto(String filename);

}