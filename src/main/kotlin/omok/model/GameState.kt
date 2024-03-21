package omok.model

sealed class GameState(val board: Board) {
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
            override fun placeStone(
                onTurn: (GameState) -> Unit,
                onRead: () -> Position,
                onShow: (Board) -> Unit,
            ): GameState {
                onTurn(this)
                val position = onRead()

                board.placeStone(position, StoneType.BLACK_STONE)
                onShow(board)

                val omokRule =
                    OmokRule(
                        board.layout,
                        currentStone = StoneType.BLACK_STONE,
                        otherStone = StoneType.WHITE_STONE,
                        boardSize = 15,
                    )
                if (omokRule.validateOmok(
                        position.coordinate.x, position.coordinate.y,
                    ) &&
                    omokRule.checkMoreThanFive(position.coordinate.x, position.coordinate.y)
                ) {
                    return Finish(board)
                }

                return WhiteTurn(board)
            }
        }

        class WhiteTurn(board: Board) : Running(board) {
            override fun placeStone(
                onTurn: (GameState) -> Unit,
                onRead: () -> Position,
                onShow: (Board) -> Unit,
            ): GameState {
                onTurn(this)
                val position = onRead()

                board.placeStone(position, StoneType.WHITE_STONE)

                val blockRule =
                    OmokRule(
                        board.layout,
                        currentStone = StoneType.BLACK_STONE,
                        otherStone = StoneType.WHITE_STONE,
                        boardSize = 15,
                    )
                val blockBoard =
                    board.generateBlock(
                        blockRule::checkThreeThree,
                        blockRule::countFourFour,
                        blockRule::checkMoreThanFive,
                    )
                onShow(blockBoard)

                val omokRule =
                    OmokRule(
                        board.layout,
                        currentStone = StoneType.WHITE_STONE,
                        otherStone = StoneType.BLACK_STONE,
                        boardSize = 15,
                    )
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
