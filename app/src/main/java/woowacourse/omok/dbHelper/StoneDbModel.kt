package woowacourse.omok.dbHelper

import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState

enum class StoneDbModel(val value: Int) {
    BLACK(1),
    WHITE(2),
    EMPTY(0),
    ;

    companion object {
        fun of(stoneState: StoneState): StoneDbModel {
            return when (stoneState) {
                BlackStoneState -> BLACK
                WhiteStoneState -> WHITE
                EmptyStoneState -> EMPTY
            }
        }

        fun toStoneState(value: Int): StoneState {
            return when (value) {
                1 -> BlackStoneState
                2 -> WhiteStoneState
                0 -> EmptyStoneState
                else -> throw IllegalArgumentException("Invalid value: $value")
            }
        }
    }
}
