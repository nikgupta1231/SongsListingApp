package com.example.songslistingapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.songslistingapp.databinding.ItemGroupBinding

class SongGroupAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = ArrayList<SongGroup>()
    var songSpanCount = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        val binding: ItemGroupBinding =
            ItemGroupBinding.inflate(layoutInflater as LayoutInflater, parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GroupViewHolder).bind(list[position], songSpanCount)
    }

    override fun getItemCount(): Int = list.size

    class GroupViewHolder(private val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(songGroup: SongGroup, songSpanCount: Int) {
            binding.tvHeading.text = songGroup.groupName

            val songCount = songGroup.songList?.size ?: 0
            binding.rvSongsList.layoutManager = GridLayoutManager(
                binding.root.context,
                if (songCount >= songSpanCount) songSpanCount else songCount,
                GridLayoutManager.HORIZONTAL,
                false
            )

            val adapter = SongNameAdapter()
            songGroup.songList?.let { adapter.list.addAll(it) }

            binding.rvSongsList.adapter = adapter
        }

    }

}