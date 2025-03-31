package woowacourse.omok.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.domain.Game

class GameRecyclerAdapter(
    items: List<Game>,
    private val onItemClick: (Int) -> Unit,
    private val deleteListener: OnGameDeleteListener,
) : RecyclerView.Adapter<GameRecyclerAdapter.ViewHolder>() {
    private val items = items.toMutableList()

    inner class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tv_game_title)

        fun bind(
            position: Int,
            item: Game,
        ) {
            title.text = item.name
            itemView.setOnClickListener { onItemClick(item.gameId) }
            itemView.findViewById<Button>(R.id.btn_delete_game).setOnClickListener {
                val result = deleteListener.onDeleteGame(item.gameId)
                if (result) {
                    removeItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(position, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}
