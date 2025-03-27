package omok.model.domain.gameState

import omok.model.domain.rule.BlackWinRule
import omok.model.domain.rule.FourFourRule
import omok.model.domain.rule.RuleMapper
import omok.model.domain.rule.ThreeThreeRule
import omok.model.domain.rule.WhiteWinRule
import omok.model.entity.Stone
import omok.model.entity.position.Position

class TurnResult(
    private val gameState: GameState,
    private val position: Position,
) {
    fun blackTurnResult(): GameState {
        val adaptedBoard: List<List<Int>> = RuleMapper.adapt(gameState.board)
        val adaptedPosition: Pair<Int, Int> = RuleMapper.adapt(position)

        return when {
            BlackWinRule.validated(adaptedBoard, adaptedPosition) -> blackWin()
            FourFourRule.validated(adaptedBoard, adaptedPosition) -> gameState
            ThreeThreeRule.validated(adaptedBoard, adaptedPosition) -> gameState
            else -> whiteTurn()
        }
    }

    fun whiteTurnResult(): GameState {
        val adaptedBoard = RuleMapper.adapt(gameState.board)
        val adaptedPosition = RuleMapper.adapt(position)

        return when {
            WhiteWinRule.validated(adaptedBoard, adaptedPosition) -> whiteWin()
            else -> blackTurn()
        }
    }

    private fun blackWin(): GameState {
        gameState.board.put(position, Stone.BLACK)
        return gameState.copy(playing = false)
    }

    private fun whiteWin(): GameState = gameState.copy(playing = false)

    private fun blackTurn(): GameState = gameState.copy(turn = BlackTurn())

    private fun whiteTurn(): GameState {
        gameState.board.put(position, Stone.BLACK)
        return gameState.copy(turn = WhiteTurn)
    }
}
