package omok.model

import omok.library.OmokRule

sealed class GameState(val board: Board) {
    abstract fun updateState(
        onTurn: (GameState) -> Unit,
        onBoard: (Board) -> Unit,
        onPosition: () -> Position,
        onOmokRule: OmokRule,
    ): GameState

    sealed class Running(board: Board) : GameState(board) {
        abstract fun currentType(): PositionType

        abstract fun nextTurn(): GameState

        override fun updateState(
            onTurn: (GameState) -> Unit,
            onBoard: (Board) -> Unit,
            onPosition: () -> Position,
            onOmokRule: OmokRule,
        ): GameState {
            onTurn(this)
            onBoard(board)

            val position = onPosition()
            board.placeStone(position, currentType())

            if (onOmokRule.isOmok(position.coordinate.x, position.coordinate.y, board.layout)) return Finish(board)
            return (nextTurn())
        }

        class BlackTurn(board: Board) : Running(board) {
            init {
                board.setupBoard(PositionType.BLACK_STONE)
            }

            override fun currentType() = PositionType.BLACK_STONE

            override fun nextTurn() = WhiteTurn(board)
        }

        class WhiteTurn(board: Board) : Running(board) {
            init {
                board.setupBoard(PositionType.WHITE_STONE)
            }

            override fun currentType() = PositionType.WHITE_STONE

            override fun nextTurn() = BlackTurn(board)
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun updateState(
            onTurn: (GameState) -> Unit,
            onBoard: (Board) -> Unit,
            onPosition: () -> Position,
            onOmokRule: OmokRule,
        ): GameState {
            onTurn(this)
            onBoard(board)
            return this
        }
    }
}
