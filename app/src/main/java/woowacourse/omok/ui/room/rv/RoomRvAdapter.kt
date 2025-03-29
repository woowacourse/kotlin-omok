package woowacourse.omok.ui.room.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.omok.databinding.ItemRoomBinding
import woowacourse.omok.domain.room.Room

class RoomRvAdapter(
    private val onClickDelete: (Int) -> Unit,
    private val onClickJoin: (Int) -> Unit,
) : ListAdapter<Room, RoomViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomBinding.inflate(inflater, parent, false)
        return RoomViewHolder(binding, onClickDelete, onClickJoin)
    }

    override fun onBindViewHolder(
        holder: RoomViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Room>() {
                override fun areItemsTheSame(
                    oldItem: Room,
                    newItem: Room,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Room,
                    newItem: Room,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
