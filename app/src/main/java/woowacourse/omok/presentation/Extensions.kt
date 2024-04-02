package woowacourse.omok.presentation

import omok.model.turn.BlackTurn
import omok.model.turn.FinishedTurn
import omok.model.turn.Turn
import omok.model.turn.WhiteTurn
import woowacourse.omok.R

fun Turn.toStoneIconRes(): Int? {
    return when (this) {
        is BlackTurn -> R.drawable.black_stone
        is WhiteTurn -> R.drawable.white_stone
        is FinishedTurn -> null
    }
}
