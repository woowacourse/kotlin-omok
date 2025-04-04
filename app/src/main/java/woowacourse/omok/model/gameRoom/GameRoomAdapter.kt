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
            val weeksDiff = daysDiff / ONE_WEEK

            return when {
                minutesDiff < ONE_MINUTE -> "방금 전"
                minutesDiff < ONE_HOUR -> "${minutesDiff}분 전"
                daysDiff < ONE_DAY -> "${minutesDiff / ONE_HOUR}시간 전"
                daysDiff < ONE_WEEK -> "${daysDiff}일 전"
                daysDiff < THIRTY_DAYS -> "${weeksDiff}주 전"
                else -> time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            }
        }
    }

    companion object {
        private const val ONE_MINUTE = 1
        private const val ONE_DAY = 1
        private const val ONE_HOUR = 60
        private const val ONE_WEEK = 7
        private const val THIRTY_DAYS = 30
    }
}
