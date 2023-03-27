package woowacourse.omok.presentation.chooseroomactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.data.db.entity.Game
import woowacourse.omok.databinding.ItemRoomBinding
import woowacourse.omok.util.setOnSingleClickListener

class ChooseRoomRecyclerViewAdapter(
    private val itemClickListener: () -> Unit
) :
    ListAdapter<Game, ChooseRoomRecyclerViewHolder>(gameDiffUtil) {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChooseRoomRecyclerViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }

        val binding = ItemRoomBinding.inflate(inflater, parent, false)

        return ChooseRoomRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChooseRoomRecyclerViewHolder, position: Int) {
        holder.onBind(getItem(position), itemClickListener)
    }

    companion object {
        private val gameDiffUtil = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem.gameId == newItem.gameId

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem == newItem
        }
    }
}

class ChooseRoomRecyclerViewHolder(private val binding: ItemRoomBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Game, itemClickListener: () -> Unit) {
        binding.tvRoom.text = "${data.gameId}번째 방입니다"
        binding.root.setOnSingleClickListener {
            itemClickListener()
        }
    }
}
