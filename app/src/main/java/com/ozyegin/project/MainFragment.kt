package com.ozyegin.project

import GameListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameListAdapter

    private val dummyGames: List<Game> = listOf(
        Game(
            1,
            "The Legend of Zelda: Breath of the Wild",
            "Explore a vast and vibrant world...",
            98,
            "Over 500,000 positive reviews",
            listOf("image1.jpg", "image2.jpg", "image3.jpg"),
            "https://www.nintendo.com/games/detail/the-legend-of-zelda-breath-of-the-wild-switch/"
        ),
        Game(
            2,
            "Red Dead Redemption 2",
            "Experience the life of an outlaw...",
            97,
            "Critically acclaimed open-world adventure",
            listOf("red_dead_1.jpg", "red_dead_2.jpg", "red_dead_3.jpg"),
            "https://www.rockstargames.com/reddeadredemption2/"
        ),
        Game(
            3,
            "The Witcher 3: Wild Hunt",
            "Embark on a dark fantasy adventure...",
            94,
            "Winner of numerous Game of the Year awards",
            listOf("witcher_1.jpg", "witcher_2.jpg", "witcher_3.jpg"),
            "https://thewitcher.com/en/witcher3"
        ),
        Game(
            4,
            "God of War",
            "Journey with Kratos and Atreus...",
            94,
            "Epic action-adventure with stunning visuals",
            listOf("god_of_war_1.jpg", "god_of_war_2.jpg", "god_of_war_3.jpg"),
            "https://www.sie.com/en/games/god-of-war/"
        ),
        Game(
            5,
            "Super Mario Odyssey",
            "Join Mario on a globe-trotting adventure...",
            97,
            "Captivating platformer with creative level design",
            listOf("mario_odyssey_1.jpg", "mario_odyssey_2.jpg", "mario_odyssey_3.jpg"),
            "https://www.nintendo.com/games/detail/super-mario-odyssey-switch/"
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = GameListAdapter(dummyGames)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        // Submit the list of games to the adapter
        adapter.submitList(dummyGames)
    }
}
data class Game{
    val id: Int,
    val title: String,
    val description: String,
    val rating: Int,
    val review: String,
    val images: List<String>,
    val url: String
}
class GameListAdapter : ListAdapter<Game, GameViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)
    }
}

class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(game: Game) {
        // Bind your data to the ViewHolder views
        // Example: itemView.findViewById<TextView>(R.id.textViewTitle).text = game.title
    }
}

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {

    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}