package omok.model.state

import omok.model.Board
import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.rule.BlackPutRule
import omok.model.rule.PutRule
import omok.model.rule.WhiteCanPutRule


sealed class GameState(protected var board: Board) {

    abstract fun canPut(stone: OmokStone): Boolean

    sealed class Running(private val putRule: PutRule, board: Board) : GameState(board) {
        abstract fun put(position: Position): GameState

        override fun canPut(stone: OmokStone): Boolean {
            return putRule.canPut(stone, board)
        }

        fun isFinished(position: Position) = board.isInOmok(position)

        class BlackTurn(putRule: PutRule, board: Board) : Running(putRule, board) {
            override fun put(position: Position): GameState {
                val newStones = board + OmokStone(position, StoneColor.BLACK)
                if (newStones.isInOmok(position)) return Finish(board)
                return WhiteTurn(WhiteCanPutRule, newStones)
            }
        }

        class WhiteTurn(putRule: PutRule, board: Board) : Running(putRule, board) {
            override fun put(position: Position): GameState {
                val newStones = board + OmokStone(position, StoneColor.WHITE)
                if (newStones.isInOmok(position)) return Finish(board)
                return BlackTurn(BlackPutRule, newStones)
            }
        }
    }

    class Finish(board: Board) : GameState(board) {
        override fun canPut(stone: OmokStone): Boolean = false
//
//        fun winner(): OmokStone = board.last()
    }
}
