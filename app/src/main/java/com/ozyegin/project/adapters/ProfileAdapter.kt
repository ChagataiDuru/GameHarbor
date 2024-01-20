package com.ozyegin.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ozyegin.project.R
import com.ozyegin.project.data.Profile

class ProfileAdapter(private val profileItem: Profile) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profileItem = profileItem
        holder.bind(profileItem)
    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.profileName)
        private val passTextView: TextView = itemView.findViewById(R.id.profilePass)
        private val dateTextView: TextView = itemView.findViewById(R.id.profileDate)
        private val emailTextView: TextView = itemView.findViewById(R.id.profileEmail)


        fun bind(profileItem: Profile) {
            nameTextView.text = profileItem.name
            passTextView.text = profileItem.email
            dateTextView.text = profileItem.dateofreg
            emailTextView.text = profileItem.email
        }
    }
}
