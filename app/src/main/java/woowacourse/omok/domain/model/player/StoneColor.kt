package woowacourse.omok.domain.model.player

import woowacourse.omok.domain.model.omokboard.IntersectionState

enum class StoneColor {
    BLACK,
    WHITE,
    ;

    fun reversed(): StoneColor =
        when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
        }

    fun toIntersectionState(): IntersectionState =
        when (this) {
            BLACK -> IntersectionState.OCCUPIED_BLACK
            WHITE -> IntersectionState.OCCUPIED_WHITE
        }
}
