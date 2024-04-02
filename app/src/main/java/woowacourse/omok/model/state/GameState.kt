package woowacourse.omok.model.state

import woowacourse.omok.model.Board
import woowacourse.omok.model.Coordinate

sealed class GameState(val board: Board, val turn: Turn) {
    abstract fun placeStone(coordinate: Coordinate): GameState

    protected abstract fun getNextTurn(placeResult: PlaceResult): GameState

    sealed class Playing private constructor(board: Board, turn: Turn = Turn.Black) : GameState(board, turn) {
        init {
            setupRule()
        }

        override fun placeStone(coordinate: Coordinate): GameState {
            val placeResult = board.placeStone(coordinate)
            return getNextTurn(placeResult)
        }

        override fun getNextTurn(placeResult: PlaceResult): GameState {
            return when (placeResult) {
                PlaceResult.Block -> {
                    Block(board, turn)
                }
                PlaceResult.Duplicate -> {
                    Duplicate(board, turn)
                }
                PlaceResult.Done -> {
                    Start(board, turn.nextTurn())
                }
                PlaceResult.Omok -> {
                    Finish(board, turn)
                }
            }
        }

        private fun setupRule() {
            board.setRule(turn)
        }

        class Start(board: Board, turn: Turn = Turn.Black) : Playing(board, turn)

        class Block(board: Board, turn: Turn) : Playing(board, turn)

        class Duplicate(board: Board, turn: Turn) : Playing(board, turn)
    }

    class Finish(board: Board, turn: Turn) : GameState(board, turn) {
        override fun placeStone(coordinate: Coordinate): GameState {
            return this
        }

        override fun getNextTurn(placeResult: PlaceResult): GameState {
            return this
        }
    }
}
