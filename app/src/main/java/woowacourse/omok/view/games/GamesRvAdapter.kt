package woowacourse.omok.view.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.domain.Game

class GamesRvAdapter(
    games: List<Game>,
    private val listener: OnGameClickListener,
) : RecyclerView.Adapter<GamesRvAdapter.GameViewHolder>() {
    private val games: MutableList<Game> = games.toMutableList()

    fun updateGames(gameId: Int) {
        val gameIndex = games.indexOfFirst { it.id == gameId }
        if (gameIndex != -1) {
            games.removeAt(gameIndex)
            notifyItemRemoved(gameIndex)
        }
    }

    inner class GameViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_game_title)
        private val tvGameStatus: TextView = itemView.findViewById(R.id.tv_game_status)
        private val deleteButton: Button = itemView.findViewById(R.id.btn_game_delete)

        fun bind(game: Game) {
            tvTitle.text = game.title

            tvGameStatus.text = game.isFinished.toStatusText()
            tvGameStatus.setTextColor(game.isFinished.toStatusTextColor())

            deleteButton.setOnClickListener { listener.deleteGame(game.id) }
            itemView.setOnClickListener { listener.enterGame(game.id) }
        }

        private fun Boolean.toStatusText(): String {
            val resId =
                if (this) {
                    R.string.game_status_finished
                } else {
                    R.string.game_status_playing
                }

            return itemView.context.getString(resId)
        }

        private fun Boolean.toStatusTextColor(): Int {
            val resId = if (this) R.color.game_status_gray else R.color.game_status_green

            return itemView.context.getColor(resId)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GameViewHolder,
        position: Int,
    ) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int = games.size

    interface OnGameClickListener {
        fun enterGame(gameId: Int)

        fun deleteGame(gameId: Int)
    }
}
