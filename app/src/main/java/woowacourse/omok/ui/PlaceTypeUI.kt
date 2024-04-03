package woowacourse.omok.ui

import woowacourse.omok.R
import woowacourse.omok.model.game.BlackStonePlace
import woowacourse.omok.model.game.DoubleFourPlace
import woowacourse.omok.model.game.DoubleOpenThreePlace
import woowacourse.omok.model.game.DuplicationPlace
import woowacourse.omok.model.game.OverlinePlace
import woowacourse.omok.model.game.PlaceType
import woowacourse.omok.model.game.WhiteStonePlace

fun PlaceType.message(): Int {
    return when (this) {
        DoubleOpenThreePlace -> R.string.double_open_three_place
        DoubleFourPlace -> R.string.double_four_place
        OverlinePlace -> R.string.overline_place
        DuplicationPlace -> R.string.duplication_place
        BlackStonePlace,
        WhiteStonePlace,
        -> R.string.empty
    }
}
