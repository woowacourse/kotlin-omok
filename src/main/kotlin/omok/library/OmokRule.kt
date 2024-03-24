package omok.library

import omok.model.PositionType

interface OmokRule {
    fun isOmok(
        x: Int,
        y: Int,
        board: Array<Array<PositionType>>,
    ): Boolean
}
