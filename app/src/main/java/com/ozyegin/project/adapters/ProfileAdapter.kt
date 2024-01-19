package com.ozyegin.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ozyegin.project.R
import com.ozyegin.project.data.Profile

class ProfileAdapter(private val profileItems: List<Profile>) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profileItem = profileItems[position]
        holder.bind(profileItem)
    }

    override fun getItemCount(): Int {
        return profileItems.size
    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.profileItemTitle)
        private val valueTextView: TextView = itemView.findViewById(R.id.profileItemValue)

        fun bind(profileItem: Profile) {
            titleTextView.text = profileItem.title
            valueTextView.text = profileItem.value
        }
    }
}
