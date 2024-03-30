package woowacourse.omok.view

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone

interface OmokView {
    fun updateBoard(board: Board)

    fun updateSingleSpace(omokStone: OmokStone)
}
