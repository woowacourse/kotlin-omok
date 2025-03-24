package omok.model.board

import omok.model.stone.StoneState

class BoardImpl private constructor(
    override val board: Map<Position, StoneState>,
) : Board {
    fun board(): Map<Position, StoneState> = board.toMap()

    override fun canPlaceStone(position: Position): Boolean = position.canPlace()

    override fun placeStone(
        position: Position,
        stoneState: StoneState,
    ): BoardImpl {
        val newBoard = board.toMutableMap()

        newBoard[position] = stoneState

        return BoardImpl(newBoard.toMap())
    }

    override fun stoneState(position: Position): StoneState = position.stoneState()

    override fun isFull(): Boolean = board.values.all { it != StoneState.NONE }

    companion object {
        private const val Y_MAX_RANGE = 15
        private const val Y_MIN_RANGE = 1
        private const val X_MAX_RANGE = 15
        private const val X_MIN_RANGE = 1

        fun createEmpty(): BoardImpl {
            val initialBoard = mutableMapOf<Position, StoneState>()
            for (x in X_MIN_RANGE..X_MAX_RANGE) {
                for (y in Y_MIN_RANGE..Y_MAX_RANGE) {
                    initialBoard[Position.from(x, y)] = StoneState.NONE
                }
            }
            return BoardImpl(initialBoard.toMap())
        }
    }
}
