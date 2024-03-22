package omok.model.state

import omok.model.Board
import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.rule.OmokGameRule
import omok.model.rule.RenjuRule

sealed class GameState(val board: Board) {
    abstract val isFinished: Boolean

    val winner get() = if (isFinished) board.lastOrNull() else null

    abstract fun put(onPlace: () -> Position): GameState

    sealed class Running(private val putRule: OmokGameRule, board: Board) : GameState(board) {
        protected fun canPut(stone: OmokStone): Boolean {
            return putRule.canPlaceStone(stone, board)
        }

        class BlackTurn(board: Board) : Running(RenjuRule, board) {
            override val isFinished = false

            override fun put(onPlace: () -> Position): GameState {
                val position = onPlace()
                val newStone = OmokStone(position, StoneColor.BLACK)
                if (canPut(newStone)) {
                    val newBoard = board + newStone
                    if (newBoard.isInOmok(position)) return Finish(newBoard)
                    return WhiteTurn(newBoard)
                }
                return put(onPlace)
            }
        }

        class WhiteTurn(board: Board) : Running(whiteStoneRule, board) {
            override val isFinished = false

            override fun put(onPlace: () -> Position): GameState {
                val position = onPlace()
                val newStone = OmokStone(position, StoneColor.WHITE)
                if (canPut(newStone)) {
                    val newBoard = board + newStone
                    if (newBoard.isInOmok(position)) return Finish(newBoard)
                    return BlackTurn(newBoard)
                }
                return put(onPlace)
            }

            companion object {
                private val whiteStoneRule: OmokGameRule = OmokGameRule { stone, board -> board.canPlace(stone) }
            }
        }
    }

    class Finish(board: Board) : GameState(board) {
        override val isFinished = true

        override fun put(onPlace: () -> Position): GameState {
            error("게임이 이미 종료됐습니다.")
        }
    }
}
