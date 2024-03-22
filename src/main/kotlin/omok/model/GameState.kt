package omok.model

sealed class GameState(val board: Board) {
    abstract fun placeStone(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ): GameState

    abstract fun getNextTurn(): GameState

    abstract fun getCurrentStoneType(): PositionType

    abstract fun setupRule()

    sealed class Running(board: Board) : GameState(board) {
        override fun placeStone(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            onShow(board)
            onTurn(this)

            val position = onRead()
            board.placeStone(position, getCurrentStoneType())

            if (board.isOmok(position, getCurrentStoneType())) return Finish(board)
            return getNextTurn()
        }

        class BlackTurn(board: Board) : Running(board) {
            init {
                setupRule()
            }

            override fun setupRule() {
                board.setupOmokRule(PositionType.BLACK_STONE)
            }

            override fun getNextTurn(): GameState = WhiteTurn(board)

            override fun getCurrentStoneType(): PositionType = PositionType.BLACK_STONE
        }

        class WhiteTurn(board: Board) : Running(board) {
            init {
                setupRule()
            }

            override fun setupRule() {
                board.setupOmokRule(PositionType.WHITE_STONE)
            }

            override fun getNextTurn(): GameState = BlackTurn(board)

            override fun getCurrentStoneType(): PositionType = PositionType.WHITE_STONE
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun getCurrentStoneType(): PositionType {
            throw IllegalStateException(ERROR_INVALID_CALL)
        }

        override fun setupRule() {
            throw IllegalStateException(ERROR_INVALID_CALL)
        }

        override fun getNextTurn(): GameState {
            throw IllegalStateException(ERROR_INVALID_CALL)
        }

        override fun placeStone(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            return this
        }

        companion object {
            const val ERROR_INVALID_CALL = "게임이 종료된 상황입니다"
        }
    }
}
