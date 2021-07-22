package com.example.photographycustomer.Room;

import android.content.Context;

import androidx.room.Room;

public class CustomerDB {

    private Context context;
    private static CustomerDB mInstance;
    private RoomDB roomDB;

    public CustomerDB(Context context) {
        this.context = context;
        roomDB = Room.databaseBuilder(context, RoomDB.class, "Customer").build();
    }

    public static synchronized CustomerDB getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new CustomerDB(mCtx);
        }
        return mInstance;
    }

    public RoomDB getRoomDB() {
        return roomDB;
    }

}
