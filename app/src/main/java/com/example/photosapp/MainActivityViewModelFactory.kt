package com.example.photosapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photosapp.database.PhotoDao

class MainActivityViewModelFactory(private val dao: PhotoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}