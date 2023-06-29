package com.example.photosapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photosapp.database.AppDatabase
import com.example.photosapp.database.PhotoDao
import com.example.photosapp.database.PhotoItem
import com.example.photosapp.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var retrofitService: PhotoService
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var photoAdapter : PhotosRecyclerView

    private lateinit var photosDao : PhotoDao

    private var notificationManager : NotificationManager? = null
    private val KEY_REPLY = "key_reply"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        photosDao = AppDatabase.getInstance(application).photoDao()

        val factory = MainActivityViewModelFactory(photosDao)
        viewModel = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]
        retrofitService = RetrofitInstance.getInstance().create(PhotoService::class.java)

        createNotificationChannel("com.example.photos.channel1")

        binding.rvPhotos.layoutManager = GridLayoutManager(this, 2)
        photoAdapter = PhotosRecyclerView {
            val notification =
                NotificationCompat.Builder(this@MainActivity, "com.example.photos.channel1")
                    .setContentTitle(it)
                    .setSmallIcon(androidx.core.R.drawable.notification_bg)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
            notificationManager?.notify(45, notification)
        }
        binding.rvPhotos.adapter = photoAdapter



        binding.btnRefresh.setOnClickListener {
            val responseLiveData : LiveData<Response<Photos>> = liveData {
                val response = retrofitService.getImages()
                emit(response)
            }
            responseLiveData.observe(this) {
                val photos = it.body()
                if (photos != null) {
                    Toast.makeText(this, "Received New Photos", Toast.LENGTH_LONG).show()
                    val save = Photos()
                    save.addAll(photos.slice(0..20))
                    viewModel.updatePhoto(save)
                }
            }
        }

        binding.btnClear.setOnClickListener {
            Toast.makeText(this, "Cleared Local Data", Toast.LENGTH_LONG).show()
            viewModel.clearPhotos()
        }


        viewModel.photos.observe(this) {
            val photos = Photos()
            photos.addAll(it)
            photoAdapter.updatePhotos(photos)
            photoAdapter.notifyDataSetChanged()
        }
    }

    private fun createNotificationChannel(channelID : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelID, "name", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "channelDescription"
            }

            notificationManager?.createNotificationChannel(channel)
        }
    }
}