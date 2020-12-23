package com.example.githubuserapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.adapter.UserAdapter.MyViewHolder
import com.example.githubuserapp.main.DetailUserActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.model.UserModel
import org.parceler.Parcels
import java.util.*

class UserAdapter(private val listData: ArrayList<UserModel>) :
    RecyclerView.Adapter<MyViewHolder>() {
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

        //Pindah ke Detail Activity
        holder.itemView.setOnClickListener {
            val moveDetailActivity = Intent(context, DetailUserActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(DATA_USER, Parcels.wrap<UserModel>(data[position]))
            moveDetailActivity.putExtra(DATA_EXTRA, bundle)
            context.startActivity(moveDetailActivity)
        }
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