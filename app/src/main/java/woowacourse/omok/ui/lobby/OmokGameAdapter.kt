package woowacourse.omok.ui.lobby

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.omok.domain.model.game.OmokGameEntity

class OmokGameAdapter(
    private val onEntranceClick: (Int) -> Unit,
) : ListAdapter<OmokGameEntity, OmokGameViewHolder>(OmokGameDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OmokGameViewHolder = OmokGameViewHolder.from(parent, onEntranceClick)

    override fun onBindViewHolder(
        holder: OmokGameViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class OmokGameDiffCallback : DiffUtil.ItemCallback<OmokGameEntity>() {
        override fun areItemsTheSame(
            oldItem: OmokGameEntity,
            newItem: OmokGameEntity,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: OmokGameEntity,
            newItem: OmokGameEntity,
        ): Boolean = oldItem == newItem
    }
}
