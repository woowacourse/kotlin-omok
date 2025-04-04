package woowacourse.omok

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class RoomData(
    val roomId: Int,
)

class RoomListAdapter(
    private val roomList: MutableList<RoomData>,
) : RecyclerView.Adapter<RoomListAdapter.RoomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RoomViewHolder {
        val itemView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.room_item, parent, false)
        return RoomViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: RoomViewHolder,
        position: Int,
    ) {
        val item = roomList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = roomList.count()

    inner class RoomViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val roomId: TextView = itemView.findViewById<TextView>(R.id.room_id)
        private val btnJoin: Button = itemView.findViewById<Button>(R.id.btn_join_room)

        fun bind(item: RoomData) {
            roomId.text = "${item.roomId + 1}번 방"

            btnJoin.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("ROOM_ID", item.roomId + 1)
                context.startActivity(intent)
            }
        }
    }

    fun addRoom(room: RoomData) {
        roomList.add(room)
        notifyItemInserted(roomList.size - 1)
    }
}
