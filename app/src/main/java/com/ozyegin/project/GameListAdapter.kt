import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ozyegin.project.R
import com.ozyegin.project.data.Game

class GameListAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]

        // Load image using your preferred image loading library (Glide, Picasso, etc.)
        // Example using Glide:
        // Glide.with(holder.itemView)
        //    .load(game.imageUrl)
        //    .into(holder.gameImage)

        holder.gameTitle.text = game.title
        holder.gameScore.text = "Score: ${game.score}"

        holder.itemView.setOnClickListener {
            // Handle item click event
        }
    }

    override fun getItemCount(): Int = games.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameImage: ImageView = itemView.findViewById(R.id.game_image)
        val gameTitle: TextView = itemView.findViewById(R.id.game_title)
        val gameScore: TextView = itemView.findViewById(R.id.game_score)
    }
}
