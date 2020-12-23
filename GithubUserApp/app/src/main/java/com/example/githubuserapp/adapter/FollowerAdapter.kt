package com.example.githubuserapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.FollowerAdapter.FollowerViewHolder
import com.example.githubuserapp.model.FollowerModel
import java.util.ArrayList

class FollowerAdapter(private val context: Context?, listFollower: ArrayList<FollowerModel>) : RecyclerView.Adapter<FollowerViewHolder>() {
    private var data = ArrayList<FollowerModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val itemview = LayoutInflater.from(context).inflate(R.layout.follower_items, parent, false)
        return FollowerViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.tvUsernameFollower.text = data[position].login
        context?.let {
            Glide.with(it)
                .load(data[position].avatarUrl)
                .into(holder.ivAvatarFollower)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsernameFollower: TextView
        var ivAvatarFollower: ImageView

        init {
            tvUsernameFollower = itemView.findViewById(R.id.tv_username_follower)
            ivAvatarFollower = itemView.findViewById(R.id.iv_avatar_follower)
        }
    }

    init {
        data = listFollower
    }


}