package omok.model

sealed class GameState(var board: Board) {
    abstract fun placeStone(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ): GameState

    sealed class Running(board: Board) : GameState(board) {
        companion object {
            private const val BOARD_SIZE = 15
        }

        class BlackTurn(board: Board) : Running(board) {
            private val omokRule: OmokRule

            init {
                omokRule =
                    OmokRule(
                        board.layout,
                        currentStone = PositionType.BLACK_STONE,
                        otherStone = PositionType.WHITE_STONE,
                        boardSize = BOARD_SIZE,
                    )
                this.board =
                    board.generateBlock(
                        omokRule::checkThreeThree,
                        omokRule::countFourFour,
                        omokRule::checkMoreThanFive,
                    )
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

                if (omokRule.validateOmok(
                        position.coordinate.x, position.coordinate.y,
                    ) &&
                    !omokRule.checkMoreThanFive(position.coordinate.x, position.coordinate.y)
                ) {
                    return Finish(board)
                }
                return WhiteTurn(board)
            }
        }

        class WhiteTurn(board: Board) : Running(board) {
            private val omokRule: OmokRule

            init {
                this.board.removeBlock()
                omokRule =
                    OmokRule(
                        board.layout,
                        currentStone = PositionType.WHITE_STONE,
                        otherStone = PositionType.BLACK_STONE,
                        boardSize = BOARD_SIZE,
                    )
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

                if (omokRule.validateOmok(position.coordinate.x, position.coordinate.y)) {
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
