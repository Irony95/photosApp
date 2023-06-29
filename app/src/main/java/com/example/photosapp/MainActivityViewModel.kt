package com.example.photosapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photosapp.database.PhotoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val dao : PhotoDao) : ViewModel() {
    val photos = dao.getLiveDataPhotos()

    fun updatePhoto(_photos : Photos) = viewModelScope.launch(Dispatchers.IO) {

            clearPhotos()
            for (photo in _photos) {
                dao.insertPhoto(photo)
            }
            Log.i("test", "completed")
            Log.i("test", photos.value?.size.toString())
    }

    fun clearPhotos() = viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAllPhotos()
            Log.i("test", photos.value?.size.toString())
    }
}