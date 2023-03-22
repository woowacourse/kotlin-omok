package woowacourse.omok

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.data.RefreshButton
import woowacourse.omok.data.RoomInfo
import woowacourse.omok.data.RoomListType

class CustomAdapter(
    private val clickRefresh: () -> Unit,
    private val showProductDetail: (RoomInfo) -> Unit,
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

        fun bind(roomInfo: RoomInfo) {
            textView.text = roomInfo.title
            statusView.text = roomInfo.status
            timeView.text = roomInfo.time
            playerView.text = roomInfo.player.name
            overallResultView.text = "%d승 %d패 %d무".format(roomInfo.player.overallRecord.win, roomInfo.player.overallRecord.lose, roomInfo.player.overallRecord.draw)
            imageView.setImageResource(roomInfo.player.profile)
            itemView.setOnClickListener {
                showProductDetail(roomInfo)
            }
        }
    }

    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            this.textView = view.findViewById(R.id.titleView)
        }

        fun bind(refreshButton: RefreshButton) {
            textView.text = refreshButton.title
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
            is RoomViewHolder -> holder.bind(getItem(position) as RoomInfo)
            is FooterViewHolder -> holder.bind(getItem(position) as RefreshButton)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is RoomInfo -> TYPE_ITEM
            is RefreshButton -> TYPE_FOOTER
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
