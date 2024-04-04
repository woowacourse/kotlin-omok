package woowacourse.omok.view

import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStone

interface OmokView {
    fun updateBoard(board: Board)

    fun updateSingleSpace(omokStone: OmokStone)
}
