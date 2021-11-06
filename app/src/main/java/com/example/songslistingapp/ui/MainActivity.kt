package com.example.songslistingapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songslistingapp.R
import com.example.songslistingapp.databinding.ActivityMainBinding
import com.example.songslistingapp.util.GroupType
import com.example.songslistingapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var songGroupAdapter: SongGroupAdapter

    private val clickListener = View.OnClickListener {
        when (it?.id) {
            binding.ivGroup.id -> {
                displayGroupTypeDialog()
            }
            binding.ivSpanCount.id -> {
                displaySpanCountDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.clickListener = clickListener
        binding.progressBar.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.tvGroupListSpanCount.text = viewModel.groupSpanCount.toString()
        binding.tvGroupHeader.text = when (viewModel.groupType) {
            is GroupType.GroupByArtistName -> getString(R.string.group_by_artist)
            is GroupType.GroupByAlbumName -> getString(R.string.group_by_album)
        }

        viewModel.dataLiveData.observe(this, {
            if (it == null) return@observe
            songGroupAdapter.list.clear()
            songGroupAdapter.list.addAll(it.toList())
            songGroupAdapter.songSpanCount =
                binding.tvGroupListSpanCount.text.toString().toIntOrNull() ?: 2
            songGroupAdapter.notifyDataSetChanged()
        })

        viewModel.progressState.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        "Something went wrong, Please try again later.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        songGroupAdapter = SongGroupAdapter()
        binding.recyclerView.adapter = songGroupAdapter
    }

    private fun displayGroupTypeDialog() {
        val groupTypeList = arrayListOf(
            getString(R.string.group_by_album),
            getString(R.string.group_by_artist)
        )
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adapter.addAll(groupTypeList)

        val dialog = AlertDialog.Builder(this)
        dialog.setAdapter(adapter) { _, which ->
            when (which) {
                0 -> {
                    binding.tvGroupHeader.text = groupTypeList[0]
                    viewModel.updateGroupType(GroupType.GroupByAlbumName)
                }
                1 -> {
                    binding.tvGroupHeader.text = groupTypeList[1]
                    viewModel.updateGroupType(GroupType.GroupByArtistName)
                }
            }
        }
        dialog.show()
    }

    private fun displaySpanCountDialog() {
        val spanCountList = arrayListOf(2, 3, 4, 5)
        val adapter = ArrayAdapter<Int>(this, android.R.layout.simple_selectable_list_item)
        adapter.addAll(spanCountList)

        AlertDialog.Builder(this)
            .setAdapter(adapter) { _, which ->
                binding.tvGroupListSpanCount.text = spanCountList[which].toString()
                viewModel.groupSpanCount = spanCountList[which]
                songGroupAdapter.songSpanCount = spanCountList[which]
                songGroupAdapter.notifyDataSetChanged()
            }
            .show()
    }

}