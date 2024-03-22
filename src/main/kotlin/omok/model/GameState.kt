package omok.model

sealed class GameState(val board: Board) {
    abstract fun placeStone(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ): GameState

    sealed class Running(board: Board) : GameState(board) {
        class BlackTurn(board: Board) : Running(board) {
            init {
                board.setupOmokRule(PositionType.BLACK_STONE)
            }

            override fun placeStone(
                onTurn: (GameState) -> Unit,
                onRead: () -> Position,
                onShow: (Board) -> Unit,
            ): GameState {
                onShow(board)
                onTurn(this)

                val position = onRead()
                board.placeStone(position, PositionType.BLACK_STONE)

                if (board.isOmok(position, PositionType.BLACK_STONE)) {
                    return Finish(board)
                }
                return WhiteTurn(board)
            }
        }

        class WhiteTurn(board: Board) : Running(board) {
            init {
                board.setupOmokRule(PositionType.WHITE_STONE)
            }

            override fun placeStone(
                onTurn: (GameState) -> Unit,
                onRead: () -> Position,
                onShow: (Board) -> Unit,
            ): GameState {
                onShow(board)
                onTurn(this)

                val position = onRead()
                board.placeStone(position, PositionType.WHITE_STONE)

                if (board.isOmok(position, PositionType.WHITE_STONE)) {
                    return Finish(board)
                }
                return BlackTurn(board)
            }
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun placeStone(
            onTurn: (GameState) -> Unit,
            onRead: () -> Position,
            onShow: (Board) -> Unit,
        ): GameState {
            TODO("Not yet implemented")
        }
    }
}
