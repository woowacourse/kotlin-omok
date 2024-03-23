package omok.model.game

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import omok.model.rule.OmokGameRule
import omok.model.rule.RenjuRule

sealed class GameState(val board: Board) {
    abstract val isFinished: Boolean

    abstract fun placeStone(onPlace: () -> Position): GameState

    sealed class Running(private val putRule: OmokGameRule, board: Board) : GameState(board) {
        override val isFinished = false

        private fun canPut(stone: OmokStone): Boolean {
            return putRule.canPlaceStone(stone, board)
        }

        protected fun placeStone(
            stoneColor: StoneColor,
            onDetermineTurn: (Board) -> Running,
            onPlace: () -> Position,
        ): GameState {
            val position = onPlace()
            val newStone = OmokStone(position, stoneColor)
            if (canPut(newStone)) {
                val newBoard = board + newStone
                if (newBoard.isInOmok(position)) return Finish(newBoard)
                return onDetermineTurn(newBoard)
            }
            return placeStone(stoneColor, onDetermineTurn, onPlace)
        }

        class BlackTurn(board: Board) : Running(RenjuRule, board) {
            override fun placeStone(onPlace: () -> Position): GameState {
                return super.placeStone(StoneColor.BLACK, ::WhiteTurn, onPlace)
            }
        }

        class WhiteTurn(board: Board) : Running(whiteStoneRule, board) {
            override fun placeStone(onPlace: () -> Position): GameState {
                return super.placeStone(StoneColor.WHITE, ::BlackTurn, onPlace)
            }

            companion object {
                private val whiteStoneRule: OmokGameRule = OmokGameRule { stone, board -> board.canPlace(stone) }
            }
        }
    }

    class Finish(board: Board) : GameState(board) {
        override val isFinished = true

        override fun placeStone(onPlace: () -> Position): GameState {
            error("게임이 이미 종료됐습니다.")
        }
    }
}
