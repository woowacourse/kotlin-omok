package omok.model.game.state

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import omok.model.rule.OmokGameRule

sealed class Running(private val putRule: OmokGameRule, board: Board) : GameState(board) {
    private fun canPut(stone: OmokStone): Boolean {
        return putRule.canPlaceStone(stone, board)
    }

    protected tailrec fun placeStone(
        stoneColor: StoneColor,
        onDetermineTurn: (Board) -> Running,
        onPlace: () -> Position,
    ): GameState {
        val position = onPlace()
        val newStone = OmokStone(position, stoneColor)
        if (canPut(newStone)) {
            val newBoard = board + newStone
            if (newBoard.isInOmok(position)) return Finish(newBoard)
            return onDetermineTurn(newBoard)
        }
        return placeStone(stoneColor, onDetermineTurn, onPlace)
    }
}
