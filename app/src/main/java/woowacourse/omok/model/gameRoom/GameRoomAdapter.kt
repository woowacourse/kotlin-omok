package woowacourse.omok.model.gameRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class GameRoomAdapter(
    private val gameRooms: List<GameRoom>,
    private val onItemClick: (GameRoom) -> Unit,
) : RecyclerView.Adapter<GameRoomAdapter.GameRoomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): GameRoomViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.game_room, parent, false)
        return GameRoomViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GameRoomViewHolder,
        position: Int,
    ) {
        val gameRoom = gameRooms[position]
        holder.bind(gameRoom)
    }

    override fun getItemCount(): Int = gameRooms.size

    inner class GameRoomViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val roomNameTextView: TextView = itemView.findViewById(R.id.text_room_name)
        private val dateTextView: TextView = itemView.findViewById(R.id.text_date)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(gameRooms[position])
                }
            }
        }

        fun bind(gameRoom: GameRoom) {
            val vsText = "${gameRoom.blackStonePlayerName}(흑돌) vs ${gameRoom.whiteStonePlayerName}(백돌)"
            roomNameTextView.text = vsText

            val formattedTime = formatTimeRelative(gameRoom.lastPlayTime)

            dateTextView.text = formattedTime
        }

        private fun formatTimeRelative(time: LocalDateTime): String {
            val now = LocalDateTime.now()
            val minutesDiff = ChronoUnit.MINUTES.between(time, now)
            val daysDiff = ChronoUnit.DAYS.between(time, now)
            val weeksDiff = daysDiff / 7

            return when {
                minutesDiff < 1 -> "방금 전"
                minutesDiff < 60 -> "${minutesDiff}분 전"
                daysDiff < 1 -> "${minutesDiff / 60}시간 전"
                daysDiff < 7 -> "${daysDiff}일 전"
                daysDiff < 30 -> "${weeksDiff}주 전"
                else -> time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            }
        }
    }
}
