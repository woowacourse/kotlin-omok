package woowacourse.omok.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R

class RoomsAdapter(private val clickItem: (Int) -> Unit) :
    RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {
    private val rooms = mutableListOf<Room>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun getItemCount(): Int = rooms.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.onBind(rooms[position], clickItem)
    }

    fun initRooms(newRooms: List<Room>) {
        rooms.clear()
        rooms.addAll(newRooms)
        notifyDataSetChanged()
    }

    class RoomViewHolder(roomLayout: View) : RecyclerView.ViewHolder(roomLayout) {

        fun onBind(data: Room, clickItem: (Int) -> Unit) {
            setData(data)
            itemClicked(data) { clickItem(it) }
        }

        private fun setData(data: Room) {
            val roomTitle = itemView.findViewById<TextView>(R.id.roomTitleText)
            val userNames = itemView.findViewById<TextView>(R.id.usersNameText)
            roomTitle.text = data.roomTitle
            userNames.text = data.firstUserEntity.userName + ", " + data.secondUserEntity.userName
        }

        private fun itemClicked(data: Room, clickItem: (Int) -> Unit) {
            itemView.setOnClickListener {
                clickItem(data.roomId)
            }
        }
    }
}
