package com.example.avcapp.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.avcapp.R


class ProfileRecyclerViewAdapter(
    private var mContext: Context?,
    private var mData: List<ProfileCard>?
) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_settings_profile, parent, false)
        return MyViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardTitle.text = mData!![position].getTitle()
        holder.cardValue.text = mData!![position].getProfileValue()
        holder.cardImage.setImageResource(mData!![position].getImage())
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardTitle: TextView = itemView.findViewById(R.id.profile_card_title)
        var cardValue: TextView = itemView.findViewById(R.id.profile_card_value)
        var cardImage: ImageView = itemView.findViewById(R.id.profile_card_image) as ImageView
    }
}