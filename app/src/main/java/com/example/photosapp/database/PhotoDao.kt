package com.example.photosapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {

    @Insert
    fun insertPhoto(item : PhotoItem)

    @Query("DELETE FROM photos_data")
    fun deleteAllPhotos()

    @Query("SELECT * FROM photos_data")
    fun getLiveDataPhotos() : LiveData<List<PhotoItem>>

}
