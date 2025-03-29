package woowacourse.omok

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model.Stone
import woowacourse.omok.view.RoomData

class CustomAdapter(
    private val dataSet: List<RoomData>,
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roomUserName: TextView = view.findViewById(R.id.RoomUserName)
        val roomStoneCount: TextView = view.findViewById(R.id.RoomStoneCount)
        val roomId: TextView = view.findViewById(R.id.RoomId)
        var stones: List<Stone> = listOf()

        fun bind(roomData: RoomData) {
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("nickname", roomData.nickname)
                    putExtra("room_id", roomData.Id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.room_item_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val roomData = dataSet[position]
        viewHolder.roomUserName.text = roomData.nickname
        viewHolder.roomStoneCount.text = roomData.stoneCount.toString() + "수 째"
        viewHolder.roomId.text = roomData.Id.toString()
        viewHolder.stones = roomData.stones
        viewHolder.bind(roomData)
    }

    override fun getItemCount() = dataSet.size
}
