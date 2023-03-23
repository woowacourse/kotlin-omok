package woowacourse.omok

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.data.Room
import woowacourse.omok.data.RoomListType
import woowacourse.omok.data.RoomRefresh

class CustomAdapter(
    private val clickRefresh: () -> Unit,
    private val showProductDetail: (Room) -> Unit,
) : ListAdapter<RoomListType, RecyclerView.ViewHolder>(diffUtil) {
    inner class RoomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        val statusView: TextView
        val timeView: TextView
        val playerView: TextView
        val overallResultView: TextView

        init {
            this.textView = view.findViewById(R.id.titleView)
            this.imageView = view.findViewById(R.id.imageView)
            this.statusView = view.findViewById(R.id.statusView)
            this.timeView = view.findViewById(R.id.timeView)
            this.playerView = view.findViewById(R.id.playerNameView)
            this.overallResultView = view.findViewById(R.id.overallResultView)
        }

        fun bind(room: Room) {
            textView.text = room.title
            statusView.text = room.status.toString()
            timeView.text = room.time.toString()
            playerView.text = room.player.name
            overallResultView.text = "%d승 %d패 %d무".format(room.player.overallRecord.win, room.player.overallRecord.lose, room.player.overallRecord.draw)
            imageView.setImageResource(room.player.profile)
            itemView.setOnClickListener {
                showProductDetail(room)
            }
        }
    }

    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            this.textView = view.findViewById(R.id.titleView)
        }

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
