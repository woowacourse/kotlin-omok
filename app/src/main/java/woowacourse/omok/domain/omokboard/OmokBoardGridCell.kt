package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.player.StoneColor

sealed class OmokBoardGridCell {
    data object Empty : OmokBoardGridCell()

    data class Occupied(val color: StoneColor) : OmokBoardGridCell()
}
