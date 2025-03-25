package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.omokboard.IntersectionState.EMPTY
import woowacourse.omok.domain.omokboard.IntersectionState.OCCUPIED_BLACK
import woowacourse.omok.domain.omokboard.IntersectionState.OCCUPIED_WHITE
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.player.StoneColor.BLACK
import woowacourse.omok.domain.player.StoneColor.WHITE

data class Intersection(
    private var _state: IntersectionState = EMPTY,
) {
    val state: IntersectionState get() = _state

    fun updateState(stoneColor: StoneColor) {
        _state =
            when (stoneColor) {
                BLACK -> OCCUPIED_BLACK
                WHITE -> OCCUPIED_WHITE
            }
    }
}
