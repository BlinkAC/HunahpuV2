package com.example.hunahpuv2.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hunahpuv2.data.model.Results
import com.example.hunahpuv2.databinding.PlaceRowItemBinding
import com.squareup.picasso.Picasso


class PlacesAdapter(private val placesList: List<Results>) :
    RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {

    inner class ViewHolder(binding: PlaceRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var photoReference: ImageView = binding.photoReference
        var placeName: TextView = binding.placeName
        var index: Int = 0
        fun bindInfo(place: Results) {
            //Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&maxheight=100&photo_reference="+place.photos[0].photoReference+"&key=AIzaSyBnWAn7Xih1ziiWBx6Ofc6sxc3w_QQWG40").into(photoReference)
            //Log.d("Images", "$index : ${place.photos[0].photoReference.toString()}")
            if(place.photos.isEmpty()){
                Log.d("Images", "No tiene")
                placeName.text = place.name
            }else{
                Log.d("Images", "Si tiene")
                Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&maxheight=100&photo_reference="+place.photos[0].photoReference+"&key=AIzaSyBnWAn7Xih1ziiWBx6Ofc6sxc3w_QQWG40").into(photoReference)
                placeName.text = place.name
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PlaceRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindInfo(placesList[position])
    }

    override fun getItemCount(): Int {
        return 10
    }
}