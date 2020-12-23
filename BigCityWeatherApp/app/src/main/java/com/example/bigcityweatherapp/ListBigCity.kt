package com.example.bigcityweatherapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

class ListBigCity(val listCity: ArrayList<City>) : RecyclerView.Adapter<ListBigCity.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: City)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_city, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val city = listCity[position]
        Glide.with(holder.itemView.context)
            .load(city.photo)
            .apply(RequestOptions().override(1000, 500))
            .into(holder.imgPhoto)
        holder.tvName.text = city.name
        holder.pop.text = city.citypop
        holder.tvCountry.text = city.country
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listCity[holder.adapterPosition]) }

        val mContext = holder.itemView.context
        holder.itemView.setOnClickListener{
            val moveDetail = Intent(mContext, WeatherDetail::class.java)

            moveDetail.putExtra("NAME", city.name)
            moveDetail.putExtra("POP", city.citypop)
            moveDetail.putExtra("PHOTO", city.photo)

            mContext.startActivity(moveDetail)
        }
    }

    override fun getItemCount(): Int {
        return listCity.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var pop: TextView = itemView.findViewById(R.id.pop)
        var tvCountry: TextView = itemView.findViewById(R.id.tv_item_country)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }
}