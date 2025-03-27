package omok.model.domain.gameState

import omok.model.domain.rule.BlackTurnResult
import omok.model.domain.rule.RuleMapper
import omok.model.entity.Stone
import omok.model.entity.board.Board
import omok.model.entity.position.Position

class BlackTurn(
    private val result: BlackTurnResult = BlackTurnResult(),
) : GameState.Playing {
    override val stone: Stone = Stone.BLACK

    override fun play(
        board: Board,
        position: Position,
    ): GameState {
        val adaptedBoard: List<List<Int>> = RuleMapper.adapt(board)
        val adaptedPosition: Pair<Int, Int> = RuleMapper.adapt(position)
        return result.nextState(adaptedBoard, adaptedPosition, board, position)
    }
}
