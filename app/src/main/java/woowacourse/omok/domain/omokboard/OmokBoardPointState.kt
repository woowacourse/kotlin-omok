package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.player.StoneColor

sealed class OmokBoardPointState {
    data object Empty : OmokBoardPointState()

    data class OCCUPIED(val color: StoneColor) : OmokBoardPointState()
}
