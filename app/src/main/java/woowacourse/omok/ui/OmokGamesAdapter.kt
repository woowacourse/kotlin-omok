package woowacourse.omok.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.domain.event.GamesItemEvent
import woowacourse.omok.ui.model.GameUiModel

class OmokGamesAdapter(private val gamesItemEvent: GamesItemEvent) :
    ListAdapter<GameUiModel, OmokGamesAdapter.ViewHolder>(GameDiffCallback()) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.game_name)
        val deleteGameButton: Button = view.findViewById(R.id.remove_game)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.omok_game_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        with(getItem(position)) {
            holder.itemView.setOnClickListener {
                gamesItemEvent.onGame(id)
            }
            holder.name.text = name
            holder.deleteGameButton.setOnClickListener {
                gamesItemEvent.onDelete(id)
            }
        }
    }

    class GameDiffCallback : DiffUtil.ItemCallback<GameUiModel>() {
        override fun areItemsTheSame(
            oldItem: GameUiModel,
            newItem: GameUiModel,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GameUiModel,
            newItem: GameUiModel,
        ): Boolean = oldItem == newItem
    }
}
