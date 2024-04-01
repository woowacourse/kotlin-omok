package woowacourse.omok.domain.model

sealed class Turn {
    var forbiddenResult: GameResult = GameResult.Success
        protected set

    abstract fun putStone(
        point: Point,
        board: Board,
    ): Turn

    companion object {
        fun determineTurn(board: Board): Turn {
            val isGameEnd = board.latestStone?.let { board.isWinCondition(it) } ?: false
            if (isGameEnd) return FinishedTurn(board.latestStone!!)

            return when (board.latestStone?.type) {
                StoneType.BLACK -> WhiteTurn()
                StoneType.WHITE -> BlackTurn()
                StoneType.EMPTY -> throw IllegalStateException()
                null -> BlackTurn()
            }
        }
    }
}

class BlackTurn : Turn() {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.BLACK, point)
        val forbiddenMoveCheckResult = board.isForbidden(stone)
        if (forbiddenMoveCheckResult != GameResult.Success) {
            forbiddenResult = forbiddenMoveCheckResult
            return this
        }
        if (point in board) {
            forbiddenResult = GameResult.DuplicatePoint
            return this
        }
        board.putStone(stone)
        if (board.isWinCondition(stone)) return FinishedTurn(stone)
        return WhiteTurn()
    }
}

class WhiteTurn : Turn() {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        val stone = Stone(StoneType.WHITE, point)
        if (point in board) {
            forbiddenResult = GameResult.DuplicatePoint
            return this
        }
        board.putStone(stone)
        if (board.isWinCondition(stone)) return FinishedTurn(stone)
        return BlackTurn()
    }
}

class FinishedTurn(val beforeStone: Stone) : Turn() {
    override fun putStone(
        point: Point,
        board: Board,
    ): Turn {
        throw IllegalStateException()
    }
}
