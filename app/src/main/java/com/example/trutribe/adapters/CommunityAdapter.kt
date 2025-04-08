package com.example.trutribe.adapters
import com.example.trutribe.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trutribe.api.UserData
import com.example.trutribe.models.CommunityModel

class CommunityAdapter (private val communitylist:ArrayList<CommunityModel>,private val onItemClick: (CommunityModel) -> Unit ):RecyclerView.Adapter<CommunityAdapter.ViewHolderClass>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.trending_card, parent,false)
        return ViewHolderClass(itemView)

    }

    override fun getItemCount(): Int {
        return communitylist.size

    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem=communitylist[position]
        holder.communityName.text=currentItem.name

        val context = holder.itemView.context
        val resourceId = context.resources.getIdentifier(
            currentItem.icon_url,
            "drawable",
            context.packageName
        )

        if (resourceId != 0) {
            holder.communityImage.setImageResource(resourceId)
        } else {
            holder.communityImage.setImageResource(R.drawable.placeholder)
        }
        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    class ViewHolderClass(itemView:View):RecyclerView.ViewHolder(itemView) {
        val communityName:TextView=itemView.findViewById(R.id.community_title)
        val communityImage:ImageView=itemView.findViewById(R.id.community_image)
    }
}