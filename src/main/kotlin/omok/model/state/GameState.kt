package omok.model.state

import omok.model.Board
import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.rule.BlackPutRule
import omok.model.rule.PutRule
import omok.model.rule.WhiteCanPutRule

// state ( context )
sealed class GameState(val board: Board) {
    val winner get() = if (this is Finish) board.lastOrNull() else null

    abstract fun put(onPlace: () -> Position): GameState

    sealed class Running(private val putRule: PutRule, board: Board) : GameState(board) {
        protected fun canPut(stone: OmokStone): Boolean {
            return putRule.canPut(stone, board)
        }

        class BlackTurn(putRule: PutRule, board: Board) : Running(putRule, board) {
            override fun put(onPlace: () -> Position): GameState {
                val position = onPlace()
                val newStone = OmokStone(position, StoneColor.BLACK)
                if (canPut(newStone)) {
                    val newBoard = board + newStone
                    if (newBoard.isInOmok(position)) return Finish(newBoard)
                    return WhiteTurn(WhiteCanPutRule, newBoard)
                }
                return put(onPlace)
            }
        }

        class WhiteTurn(putRule: PutRule, board: Board) : Running(putRule, board) {
            override fun put(onPlace: () -> Position): GameState {
                val position = onPlace()
                val newStone = OmokStone(position, StoneColor.WHITE)
                if (canPut(newStone)) {
                    val newBoard = board + newStone
                    if (newBoard.isInOmok(position)) return Finish(newBoard)
                    return BlackTurn(BlackPutRule, newBoard)
                }
                return put(onPlace)
            }
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun put(onPlace: () -> Position): GameState {
            error("게임이 이미 종료됐습니다.")
        }
    }
}
