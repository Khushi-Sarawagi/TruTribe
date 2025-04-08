package com.example.trutribe.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trutribe.R
import com.example.trutribe.models.CommunityModel

class CommunityAdapter(
    private val communityList: ArrayList<CommunityModel>, private val onItemClick: (CommunityModel) -> Unit) : RecyclerView.Adapter<CommunityAdapter.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.trending_card, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int = communityList.size

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = communityList[position]
        holder.communityName.text = currentItem.name

        val context = holder.itemView.context
        val iconName = currentItem.icon_url.substringAfterLast("/").substringBefore(".")
        val resourceId = context.resources.getIdentifier(
            iconName,
            "drawable",
            context.packageName
        )

        if (resourceId != 0) {
            holder.communityImage.setImageResource(resourceId)
        } else {
            holder.communityImage.setImageResource(R.drawable.placeholder)
        }


        try {
            val rawColor = currentItem.color.trim()
            val formattedColor = if (rawColor.startsWith("#")) rawColor else "#$rawColor"
            holder.cardLayout.setBackgroundColor(Color.parseColor(formattedColor))
        } catch (e: IllegalArgumentException) {

            holder.cardLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val communityName: TextView = itemView.findViewById(R.id.community_title)
        val communityImage: ImageView = itemView.findViewById(R.id.community_image)
        val cardLayout: RelativeLayout = itemView.findViewById(R.id.card_colour)
    }
}
