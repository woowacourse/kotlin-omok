package woowacourse.omok.roomList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.R
import woowacourse.omok.data.Room
import woowacourse.omok.data.RoomListType
import woowacourse.omok.data.RoomRefresh

class RoomListAdapter(
    private val clickRefresh: () -> Unit,
    private val showProductDetail: (Room) -> Unit,
) : ListAdapter<RoomListType, RecyclerView.ViewHolder>(diffUtil) {
    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView by lazy { view.findViewById(R.id.titleView) }
        private val imageView: ImageView by lazy { view.findViewById(R.id.imageView) }
        private val statusView: TextView by lazy { view.findViewById(R.id.statusView) }
        private val timeView: TextView by lazy { view.findViewById(R.id.timeView) }
        private val playerView: TextView by lazy { view.findViewById(R.id.playerNameView) }
        private val overallResultView: TextView by lazy { view.findViewById(R.id.overallResultView) }

        fun bind(room: Room) {
            textView.text = room.title
            statusView.text = room.status.toString()
            timeView.text = room.time.toString()
            playerView.text = room.player.name
            overallResultView.text = "%d승 %d패 %d무".format(room.player.win, room.player.lose, room.player.draw)
            imageView.setImageResource(room.player.profile)
            itemView.setOnClickListener {
                showProductDetail(room)
            }
        }
    }

    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.titleView)

        fun bind(roomRefresh: RoomRefresh) {
            textView.text = roomRefresh.title
            textView.setOnClickListener {
                clickRefresh()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_ITEM) {
            RoomViewHolder(
                layoutInflater.inflate(R.layout.recycler_row_item, parent, false),
            )
        } else {
            FooterViewHolder(
                layoutInflater.inflate(R.layout.recycler_row_item_refresh, parent, false),
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RoomViewHolder -> holder.bind(getItem(position) as Room)
            is FooterViewHolder -> holder.bind(getItem(position) as RoomRefresh)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Room -> TYPE_ITEM
            is RoomRefresh -> TYPE_FOOTER
        }

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2

        private val diffUtil = object : DiffUtil.ItemCallback<RoomListType>() {
            override fun areItemsTheSame(oldItem: RoomListType, newItem: RoomListType) =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: RoomListType, newItem: RoomListType) =
                oldItem == newItem
        }
    }
}
