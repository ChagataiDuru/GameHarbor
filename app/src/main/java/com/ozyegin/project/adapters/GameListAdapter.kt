package com.ozyegin.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import com.ozyegin.project.R
import com.ozyegin.project.data.GameList as GameListEntity

class GameListAdapter(private val dataSet: List<GameListEntity>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    private var recyclerViewGameInterface: RecyclerViewGameInterface? = null

    fun setViewItemInterface(viewItemInterface: RecyclerViewGameInterface?) {
        this.recyclerViewGameInterface = viewItemInterface
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.gameCard)
        val name: TextView = view.findViewById(R.id.textGameName)
        val textGameGenres: TextView = view.findViewById(R.id.textGameGenres)
        val textGamePlatforms: TextView = view.findViewById(R.id.textGamePlatforms)
        val cover: ImageView = view.findViewById(R.id.gameCoverImage)
        val rating: TextView = view.findViewById(R.id.textRate)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.games_list_view_holder, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = dataSet[position].name
        viewHolder.textGameGenres.text = dataSet[position].genres
        viewHolder.textGamePlatforms.text = dataSet[position].platforms
        viewHolder.rating.text = dataSet[position].rating.toString()

        Picasso.get()
            .load(imageBuilder(dataSet[position].imageId, ImageSize.COVER_SMALL, ImageType.PNG))
            .error(R.drawable.ic_baseline_no_image_24)
            .into(viewHolder.cover)

        viewHolder.card.setOnClickListener {
            recyclerViewGameInterface?.onItemClick(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size
    interface RecyclerViewGameInterface {
        fun onItemClick(gameListEntity: GameListEntity)
    }
}