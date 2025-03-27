package woowacourse.omok.domain.model.rule

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.stone.OmokStone

interface OmokRule {
    fun checkWin(
        omokStone: OmokStone,
        board: Board,
    ): Boolean

    fun checkAnyFoulCondition(
        omokStone: OmokStone,
        board: Board,
    ): Boolean
}
