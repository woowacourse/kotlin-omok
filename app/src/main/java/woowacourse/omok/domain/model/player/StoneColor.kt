package woowacourse.omok.domain.model.player

import woowacourse.omok.domain.model.omokboard.PointState

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun reversed(): StoneColor =
        when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }

    fun toIntersectionState(): PointState =
        when (this) {
            BLACK -> PointState.OCCUPIED_BLACK
            WHITE -> PointState.OCCUPIED_WHITE
        }
}
