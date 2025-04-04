package woowacourse.omok.ui.room.rv

import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.databinding.ItemRoomBinding
import woowacourse.omok.domain.room.Room

class RoomViewHolder(
    private val binding: ItemRoomBinding,
    private val onClickDelete: (Int) -> Unit,
    private val onClickJoin: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnJoinRoom.setOnClickListener {
            onClickJoin(adapterPosition)
        }
        binding.btnDeleteRoom.setOnClickListener {
            onClickDelete(adapterPosition)
        }
    }

    fun bind(item: Room) {
        binding.tvRoomName.text = item.roomName
    }
}
