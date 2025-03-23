package omok.domain.turn

import omok.domain.OmokBoard
import omok.domain.Position
import omok.domain.StoneState

interface Turn {
    val beforeTurn: StoneState?

    fun putStone(
        position: Position,
        board: OmokBoard,
    ): PutStoneResult

    companion object {
        const val ERROR_INVALID_POSITION = "잘못된 위치입니다."
        const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
