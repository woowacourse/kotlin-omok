package omok.library

import woowacourse.omok.PositionType

interface OmokRule {
    fun isOmok(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean

    fun isWin(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean
}
