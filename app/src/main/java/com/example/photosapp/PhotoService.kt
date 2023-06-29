package com.example.photosapp

import retrofit2.Response
import retrofit2.http.GET

interface PhotoService {
    @GET("/photos")
    suspend fun getImages() : Response<Photos>
}