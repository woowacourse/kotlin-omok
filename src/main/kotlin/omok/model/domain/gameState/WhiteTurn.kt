package omok.model.domain.gameState

import omok.model.domain.rule.RuleMapper
import omok.model.domain.rule.WhiteWinRule
import omok.model.entity.Stone
import omok.model.entity.position.Position

object WhiteTurn : Turn {
    override val stone: Stone = Stone.WHITE

    override fun nextGameState(
        gameState: GameState,
        position: Position,
    ): GameState {
        val adaptedBoard = RuleMapper.adapt(gameState.board)
        val adaptedPosition = RuleMapper.adapt(position)
        gameState.board.put(position, stone)
        return nextGameState(adaptedBoard, adaptedPosition, gameState)
    }

    private fun nextGameState(
        adaptedBoard: List<List<Int>>,
        adaptedPosition: Pair<Int, Int>,
        gameState: GameState,
    ): GameState =
        when {
            WhiteWinRule.validated(adaptedBoard, adaptedPosition) -> gameState.copy(playing = false)
            else -> gameState.copy(turn = BlackTurn())
        }
}
