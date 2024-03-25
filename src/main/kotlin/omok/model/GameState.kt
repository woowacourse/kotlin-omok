package omok.model

import omok.library.BlackStoneOmokRule
import omok.library.OmokRule
import omok.library.WhiteStoneOmokRule

sealed class GameState(val board: Board) {
    abstract fun updateState(
        onTurn: (GameState) -> Unit,
        onBoard: (Board) -> Unit,
        onCoordinate: () -> Coordinate,
    ): GameState

    sealed class Running(board: Board) : GameState(board) {
        abstract fun currentType(): PositionType

        abstract fun nextTurn(): GameState

        abstract fun omoRule(): OmokRule

        override fun updateState(
            onTurn: (GameState) -> Unit,
            onBoard: (Board) -> Unit,
            onCoordinate: () -> Coordinate,
        ): GameState {
            onTurn(this)
            onBoard(board)

            val position = onCoordinate()
            board.placeStone(position, currentType())

            if (omoRule().isOmok(position.x, position.y, board.getBoardLayout())) return Finish(board)
            return (nextTurn())
        }

        class BlackTurn(board: Board) : Running(board) {
            init {
                board.setupBoard(PositionType.BLACK_STONE)
            }

            override fun currentType() = PositionType.BLACK_STONE

            override fun nextTurn() = WhiteTurn(board)

            override fun omoRule() = BlackStoneOmokRule
        }

        class WhiteTurn(board: Board) : Running(board) {
            init {
                board.setupBoard(PositionType.WHITE_STONE)
            }

            override fun currentType() = PositionType.WHITE_STONE

            override fun nextTurn() = BlackTurn(board)

            override fun omoRule() = WhiteStoneOmokRule
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun updateState(
            onTurn: (GameState) -> Unit,
            onBoard: (Board) -> Unit,
            onCoordinate: () -> Coordinate,
        ): GameState {
            onTurn(this)
            onBoard(board)
            return this
        }
    }
}
