package com.example.lyrix.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lyrix.model.Track
import com.example.lyrix.MainFragmentDirections
import com.example.lyrix.R
import kotlinx.android.synthetic.main.rc_item.view.*

class RcAdapter : RecyclerView.Adapter<RcAdapter.RcViewHolder>() {
    private var trackList: List<Track> = listOf()

    private lateinit var onItemClickListener: OnClickListener

    class RcViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnClickListener {
        fun setOnItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_item, parent, false)
        return RcViewHolder(view)
    }

    override fun onBindViewHolder(holder: RcViewHolder, position: Int) {
        val currentTrack = trackList[position]
        holder.itemView.rc_track_name.text = currentTrack.track_name
        holder.itemView.rc_artist_name.text = currentTrack.artist_name
        holder.itemView.rc_item.setOnClickListener {
            onItemClickListener.setOnItemClick(position)
            val action = MainFragmentDirections.actionMainFragmentToLyricsFragment(currentTrack)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun setTracks(newList: List<Track>) {
        trackList = newList
        notifyDataSetChanged()
    }

    fun getTracks(): List<Track> {
        return trackList
    }
}