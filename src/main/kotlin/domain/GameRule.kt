package domain.domain

import domain.CoordinateState
import domain.Position

interface GameRule {

    fun isBlackWin(
        position: Position,
        board: BoardState,
    ): Boolean

    fun isWhiteWin(
        position: Position,
        board: BoardState,
    ): Boolean

    fun checkAddablePosition(board: BoardState, turn: CoordinateState, position: Position): Boolean
}
