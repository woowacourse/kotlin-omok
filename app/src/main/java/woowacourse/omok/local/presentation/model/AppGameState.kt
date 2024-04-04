package woowacourse.omok.local.presentation.model

import omok.library.BlackStoneOmokRule
import omok.library.OmokRule
import omok.library.WhiteStoneOmokRule
import omok.model.Board
import omok.model.Coordinate
import omok.model.PositionType

sealed class AppGameState(val board: Board) {
    abstract fun updateState(onCoordinate: () -> Coordinate): AppGameState

    sealed class Running(board: Board) : AppGameState(board) {
        abstract fun currentType(): PositionType

        abstract fun nextTurn(): AppGameState

        abstract fun omokRule(): OmokRule

        abstract fun updateBoard()

        override fun updateState(onCoordinate: () -> Coordinate): AppGameState {
            updateBoard()

            val position = onCoordinate()
            board.placeStone(position, currentType())

            if (omokRule().isOmok(position.x, position.y, board.getBoardLayout())) {
                return Finish(
                    board,
                )
            }
            return (nextTurn())
        }

        class BlackTurn(board: Board) : Running(board) {
            override fun currentType() = PositionType.BLACK_STONE

            override fun nextTurn() = WhiteTurn(board)

            override fun omokRule() = BlackStoneOmokRule

            override fun updateBoard() = board.removeBlock()
        }

        class WhiteTurn(board: Board) : Running(board) {
            override fun currentType() = PositionType.WHITE_STONE

            override fun nextTurn() = BlackTurn(board)

            override fun omokRule() = WhiteStoneOmokRule

            override fun updateBoard() =
                board.setBlock(
                    BlackStoneOmokRule::isThreeThree,
                    BlackStoneOmokRule::isFourFour,
                    BlackStoneOmokRule::isMoreThanFive,
                )
        }
    }

    class Finish(board: Board) : AppGameState(board) {
        override fun updateState(onCoordinate: () -> Coordinate): AppGameState {
            return this
        }
    }
}
