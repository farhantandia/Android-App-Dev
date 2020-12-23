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
import com.example.githubuserapp.model.FollowingModel
import java.util.ArrayList

class FollowingAdapter(private val context: Context?, listFollowing: ArrayList<FollowingModel>) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private var data = ArrayList<FollowingModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val itemview = LayoutInflater.from(context).inflate(R.layout.following_items, parent, false)
        return FollowingViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.tvUsernameFollowing.text = data[position].login
        Glide.with(holder.itemView.context)
            .load(data[position].avatarUrl)
            .into(holder.ivAvatarFollowing)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsernameFollowing: TextView
        var ivAvatarFollowing: ImageView

        init {
            tvUsernameFollowing = itemView.findViewById(R.id.tv_username_following)
            ivAvatarFollowing = itemView.findViewById(R.id.iv_avatar_following)
        }
    }

    init {
        data = listFollowing
    }
}
