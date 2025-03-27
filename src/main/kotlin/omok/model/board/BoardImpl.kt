package omok.model.board

import omok.model.stone.StoneState

class BoardImpl private constructor(
    override val board: MutableMap<Position, StoneState>,
) : Board {
    fun board(): Map<Position, StoneState> = board.toMap()

    override fun canPlaceStone(position: Position): Boolean = board[position] == StoneState.NONE

    override fun placeStone(
        position: Position,
        stoneState: StoneState,
    ) {
        if (canPlaceStone(position)) board[position] = stoneState else throw IllegalArgumentException("이미 돌이 놓아져 있습니다.")
    }

    override fun stoneState(position: Position): StoneState = position.stoneState()

    override fun stoneState(
        x: Int,
        y: Int,
    ): StoneState = board[Position(x, y)] ?: throw IllegalArgumentException("존재하지 않는 좌표입니다.")

    companion object {
        private const val Y_MAX_RANGE = 15
        private const val Y_MIN_RANGE = 1
        private const val X_MAX_RANGE = 15
        private const val X_MIN_RANGE = 1

        fun createEmpty(): Board {
            val initialBoard = mutableMapOf<Position, StoneState>()
            for (x in X_MIN_RANGE..X_MAX_RANGE) {
                for (y in Y_MIN_RANGE..Y_MAX_RANGE) {
                    initialBoard[Position.from(x, y)] = StoneState.NONE
                }
            }
            return BoardImpl(initialBoard)
        }
    }
}
