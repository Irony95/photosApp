package com.example.photosapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="photos_data")
data class PhotoItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="photo_id")
    val id: Int,

    @ColumnInfo(name="album_id")
    val albumId: Int,
    @ColumnInfo(name="thumbnail_url")
    val thumbnailUrl: String,
    @ColumnInfo(name="title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String,
)