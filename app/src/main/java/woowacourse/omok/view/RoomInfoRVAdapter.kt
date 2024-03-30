package woowacourse.omok.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.model.database.Room

class RoomInfoRVAdapter(
    private val rooms: List<Room>,
    private val onEnterClick: (Long, String) -> Unit,
) : RecyclerView.Adapter<RoomInfoRVAdapter.RoomInfoViewHolder>() {
    inner class RoomInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.tv_game_title)
        private val button = view.findViewById<Button>(R.id.btn_enter)

        fun bind(room: Room) {
            title.text = room.title
            button.setOnClickListener {
                val roomId = rooms[adapterPosition].id
                val title = rooms[adapterPosition].title
                onEnterClick(roomId, title)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RoomInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RoomInfoViewHolder(inflater.inflate(R.layout.item_room, parent, false))
    }

    override fun getItemCount(): Int = rooms.size

    override fun onBindViewHolder(
        holder: RoomInfoViewHolder,
        position: Int,
    ) {
        holder.bind(rooms[position])
    }
}
