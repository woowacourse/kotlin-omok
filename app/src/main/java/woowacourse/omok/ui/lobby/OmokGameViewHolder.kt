package woowacourse.omok.ui.lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.databinding.ItemLobbyRoomBinding
import woowacourse.omok.domain.model.omokboard.OmokGameEntity

class OmokGameViewHolder private constructor(
    private val binding: ItemLobbyRoomBinding,
    private val onEntranceClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(game: OmokGameEntity) {
        binding.tvRoomInfo.text = "Room No. ${game.id}\nHost. ${game.host.value}"
        binding.btnRoomEntrance.setOnClickListener {
            onEntranceClick(game.id)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onEntranceClick: (Int) -> Unit,
        ): OmokGameViewHolder {
            val binding =
                ItemLobbyRoomBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            return OmokGameViewHolder(binding, onEntranceClick)
        }
    }
}
