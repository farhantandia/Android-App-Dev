package com.example.githubconsumerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubconsumerapp.R
import java.util.*

class UserAdapter(private val listData: ArrayList<UserModel>) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    var data = ArrayList<UserModel>()
    init {
        data=listData
    }
    private lateinit var context: Context
    //1. method yang menyambungkan layout item
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        context = viewGroup.context
        val itemview: View =
            LayoutInflater.from(context).inflate(R.layout.user_items, viewGroup, false)
        return MyViewHolder(itemview)
    }

    //2. set data
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvUsername.setText(data[position].login)
        Glide.with(context)
            .load(data[position].avatarUrl)
            .into(holder.ivAvatar)

    }

    //3. jumlah data
    override fun getItemCount(): Int {
        return data.size
    }


    //4. assign komponen item
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView
        var ivAvatar: ImageView

        init {
            tvUsername = itemView.findViewById(R.id.tv_username)
            ivAvatar = itemView.findViewById(R.id.iv_avatar)
        }
    }

    companion object {
        const val DATA_USER = "datauser"
        const val DATA_EXTRA = "dataextra"
    }

    init {
        this.data = data
    }
}