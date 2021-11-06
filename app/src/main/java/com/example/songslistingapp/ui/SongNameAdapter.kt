package com.example.songslistingapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.songslistingapp.databinding.ItemSongBinding
import com.example.songslistingapp.datastore.entity.SongEntity

class SongNameAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val list = ArrayList<SongEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        val binding: ItemSongBinding =
            ItemSongBinding.inflate(layoutInflater as LayoutInflater, parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SongViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class SongViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: SongEntity) {
            binding.tvSongName.text = song.name
        }

    }

}