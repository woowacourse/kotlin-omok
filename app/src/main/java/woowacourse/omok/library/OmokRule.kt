package omok.library

import woowacourse.omok.CoordinateState

interface OmokRule {
    fun isOmok(
        x: Int,
        y: Int,
        board: Array<Array<CoordinateState>>,
    ): Boolean

    fun isWin(
        x: Int,
        y: Int,
        board: Array<Array<CoordinateState>>,
    ): Boolean
}
