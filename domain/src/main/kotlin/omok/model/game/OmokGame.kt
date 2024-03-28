package omok.model.game

import omok.model.OmokStone
import omok.model.Position
import omok.model.board.Board
import omok.model.game.state.GameState

class OmokGame(
    private var state: GameState,
    private val onEndGame: (Board, OmokStone) -> Unit,
) {
    fun placeStone(
        position: Position,
        onStartPlaced: (Board, OmokStone?) -> Unit = { _, _ -> },
        onEndPlaced: (Board, OmokStone) -> Unit = { _, _ -> },
    ): OmokGameResult {
        if (isEnd()) error("게임이 종료 되었습니다.")
        if (!canPlaceStone(position)) return InvalidGameRule
        onStartPlaced(state.board, state.board.lastStoneOrNull())
        state = state.placeStone(position)
        val (board, lastStone) = roundResult()
        onEndPlaced(board, lastStone)
        if (state.hasOmok()) onEndGame(board, lastStone)
        return Placed
    }

    private fun isEnd(): Boolean = state.hasOmok()

    private fun canPlaceStone(position: Position): Boolean = state.canPlaceStone(position)

    private fun roundResult(): RoundResult {
        val (board, lastStone) = state.board to state.board.lastStoneOrNull()
        lastStone ?: error("아직 돌을 놓지 않았습니다.")
        return RoundResult(board, lastStone)
    }

    private data class RoundResult(
        val board: Board,
        val lastStone: OmokStone,
    )
}
