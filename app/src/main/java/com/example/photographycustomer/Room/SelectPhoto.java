package com.example.photographycustomer.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "SelectPhoto", indices = @Index(value = {"selected_images"}, unique = true))
public class SelectPhoto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "selected_images")
    private String selected_images;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelected_images() {
        return selected_images;
    }

    public void setSelected_images(String selected_images) {
        this.selected_images = selected_images;
    }
}
