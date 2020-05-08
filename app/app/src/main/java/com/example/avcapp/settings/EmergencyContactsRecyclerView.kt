package com.example.avcapp.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.avcapp.R

class EmergencyContactsRecyclerView(
    private var mContext: Context?,
    private var mData: List<EmergencyContactsCard>?
) : RecyclerView.Adapter<EmergencyContactsRecyclerView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_emergency_contacts, parent, false)
        return MyViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.contactName.text = mData!![position].getName()
        holder.contactNumber.text = mData!![position].getNumber()
        holder.contactImage.setImageResource(mData!![position].getImage())
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contactName: TextView = itemView.findViewById(R.id.emergency_contact_name)
        var contactNumber: TextView = itemView.findViewById(R.id.emergency_contact_number)
        var contactImage: ImageView = itemView.findViewById(R.id.contact_image) as ImageView
    }

}