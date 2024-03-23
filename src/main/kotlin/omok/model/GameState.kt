package omok.model

sealed class GameState(val board: Board) {
    abstract fun updateState(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ): GameState

    sealed class Running(board: Board) : GameState(board) {
        abstract fun getCurrentType(): PositionType

        abstract fun getNextTurn(): GameState

        override fun updateState(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            onShow(board)
            onTurn(this)

            val position = onRead()
            board.placeStone(position, getCurrentType())

            if (board.isOmok(position, getCurrentType())) return Finish(board)
            return (getNextTurn())
        }

        class BlackTurn(board: Board) : Running(board) {
            init {
                board.setupOmokRule(PositionType.BLACK_STONE)
            }

            override fun getCurrentType() = PositionType.BLACK_STONE

            override fun getNextTurn() = WhiteTurn(board)
        }

        class WhiteTurn(board: Board) : Running(board) {
            init {
                board.setupOmokRule(PositionType.WHITE_STONE)
            }

            override fun getCurrentType() = PositionType.WHITE_STONE

            override fun getNextTurn() = BlackTurn(board)
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun updateState(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            TODO("Not yet implemented")
        }
    }
}
