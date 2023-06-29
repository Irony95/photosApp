package com.example.photosapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photosapp.database.PhotoItem
import com.example.photosapp.databinding.PhotocardBinding
import com.squareup.picasso.Picasso

class PhotosRecyclerView(val onClick: (String) -> Unit) : RecyclerView.Adapter<PhotoViewHolder>() {
    private val photos = ArrayList<PhotoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PhotocardBinding.inflate(layoutInflater, parent, false)
        return PhotoViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])

    }

    fun updatePhotos(newPhotos : Photos) {
        photos.clear()
        photos.addAll(newPhotos)
    }
}

class PhotoViewHolder(private val binding : PhotocardBinding, val onClick : (String) -> Unit) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : PhotoItem) {
        binding.root.setOnClickListener {
            onClick(item.title);
        }
        binding.tvTitle.text = item.title
        Picasso.get().load(item.url).into(binding.ivImage)
    }
}
