package omok.model.domain.gameState

import omok.model.domain.rule.BlackWinRule
import omok.model.domain.rule.FourFourRule
import omok.model.domain.rule.RuleMapper
import omok.model.domain.rule.ThreeThreeRule
import omok.model.entity.Stone
import omok.model.entity.board.Board
import omok.model.entity.position.Position

object BlackTurn : GameState.Playing {
    override val stone: Stone = Stone.BLACK

    override fun play(
        board: Board,
        position: Position,
    ): GameState {
        val adaptedBoard: List<List<Int>> = RuleMapper.adapt(board)
        val adaptedPosition: Pair<Int, Int> = RuleMapper.adapt(position)
        return when {
            BlackWinRule.validated(adaptedBoard, adaptedPosition) -> blackWin(board, position)
            FourFourRule.validated(adaptedBoard, adaptedPosition) -> this
            ThreeThreeRule.validated(adaptedBoard, adaptedPosition) -> this
            else -> whiteTurn(board, position)
        }
    }

    private fun blackWin(
        board: Board,
        position: Position,
    ): GameState.Finish {
        board.put(position, stone)
        return GameState.Finish.BLACK_WIN
    }

    private fun whiteTurn(
        board: Board,
        position: Position,
    ): WhiteTurn {
        board.put(position, stone)
        return WhiteTurn
    }
}
