package omok.model.domain.gameState

import omok.model.domain.rule.BlackWinRule
import omok.model.domain.rule.FourFourRule
import omok.model.domain.rule.RuleMapper
import omok.model.domain.rule.ThreeThreeRule
import omok.model.entity.Stone
import omok.model.entity.position.Position

class BlackTurn : Turn {
    override val stone: Stone = Stone.BLACK

    override fun nextGameState(
        gameState: GameState,
        position: Position,
    ): GameState {
        val adaptedBoard: List<List<Int>> = RuleMapper.adapt(gameState.board)
        val adaptedPosition: Pair<Int, Int> = RuleMapper.adapt(position)
        return nextGameState(adaptedBoard, adaptedPosition, gameState, position)
    }

    private fun nextGameState(
        adaptedBoard: List<List<Int>>,
        adaptedPosition: Pair<Int, Int>,
        gameState: GameState,
        position: Position,
    ): GameState =
        when {
            BlackWinRule.validated(adaptedBoard, adaptedPosition) -> blackWin(gameState, position)
            FourFourRule.validated(adaptedBoard, adaptedPosition) -> gameState
            ThreeThreeRule.validated(adaptedBoard, adaptedPosition) -> gameState
            else -> whiteTurn(gameState, position)
        }

    private fun blackWin(
        gameState: GameState,
        position: Position,
    ): GameState {
        gameState.board.put(position, stone)
        return gameState.copy(playing = false)
    }

    private fun whiteTurn(
        gameState: GameState,
        position: Position,
    ): GameState {
        gameState.board.put(position, stone)
        return gameState.copy(turn = WhiteTurn)
    }
}
