package omok.domain.omokboard

import omok.domain.player.StoneColor

sealed class OmokBoardPointState {
    data object Empty : OmokBoardPointState()

    data class OCCUPIED(val color: StoneColor) : OmokBoardPointState()
}
