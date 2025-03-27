package omok.model.domain.rule

import omok.model.domain.gameState.BlackTurn
import omok.model.domain.gameState.GameState
import omok.model.domain.gameState.WhiteTurn
import omok.model.entity.Stone
import omok.model.entity.board.Board
import omok.model.entity.position.Position

class BlackTurnResult {
    private val stone: Stone = Stone.BLACK

    fun nextState(
        adaptedBoard: List<List<Int>>,
        adaptedPosition: Pair<Int, Int>,
        board: Board,
        position: Position,
    ): GameState =
        when {
            BlackWinRule.validated(adaptedBoard, adaptedPosition) -> blackWin(board, position)
            FourFourRule.validated(adaptedBoard, adaptedPosition) -> BlackTurn()
            ThreeThreeRule.validated(adaptedBoard, adaptedPosition) -> BlackTurn()
            else -> whiteTurn(board, position)
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
