package omok.model.board

import omok.model.stone.StoneState

class OmokBoard {
    private val board = mutableMapOf<Position, StoneState>()

    init {
        for (x in 1..15) {
            for (y in 1..15) {
                val position = Position(X(x), Y(y))
                board[position] = StoneState.NONE
            }
        }
    }

    private fun canPlaceStone(position: Position): Boolean = board[position] == StoneState.NONE

    fun placeStone(
        position: Position,
        stoneState: StoneState,
    ) {
        if (canPlaceStone(position)) board[position] = stoneState
    }

    fun boardState(position: Position): StoneState = board[position] ?: throw IllegalArgumentException("잘못된 좌표입니다.")

    fun isOmok(position: Position): Boolean {
        TODO("오목인지 아닌지 판단")
    }
}
