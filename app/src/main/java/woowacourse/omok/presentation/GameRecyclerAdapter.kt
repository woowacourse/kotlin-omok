package woowacourse.omok.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R

class GameRecyclerAdapter(
    private val items: List<Pair<Int, String>>,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<GameRecyclerAdapter.ViewHolder>() {
    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tv_game_title)

        fun bind(
            item: Pair<Int, String>,
            onItemClick: (Int) -> Unit,
        ) {
            title.text = item.second
            itemView.setOnClickListener { onItemClick(item.first) }
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
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int = items.size
}
