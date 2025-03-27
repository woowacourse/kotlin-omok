package omok.model.domain.gameState

import omok.model.domain.rule.RuleMapper
import omok.model.domain.rule.WhiteWinRule
import omok.model.entity.Stone
import omok.model.entity.board.Board
import omok.model.entity.position.Position

object WhiteTurn : GameState.Playing {
    override val stone: Stone = Stone.WHITE

    override fun play(
        board: Board,
        position: Position,
    ): GameState {
        val adaptedBoard = RuleMapper.adapt(board)
        val adaptedPosition = RuleMapper.adapt(position)
        board.put(position, stone)
        if (WhiteWinRule.validated(adaptedBoard, adaptedPosition)) {
            return GameState.Finish.WHITE_WIN
        }
        return BlackTurn()
    }
}
