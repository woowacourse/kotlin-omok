package omok.model.game.state

import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor
import omok.model.board.Board
import omok.model.rule.OmokGameRule

sealed class Running(private val gameRule: OmokGameRule, board: Board) : GameState(board) {
    protected fun placeStone(
        stoneColor: StoneColor,
        position: Position,
        onDetermineTurn: (Board) -> Running,
    ): GameState {
        val newStone = OmokStone(position, stoneColor)
        if (gameRule.canPlaceStone(newStone, board)) {
            val newBoard = board.placeStone(position, stoneColor)
            if (newBoard.isInOmok(position)) return Finish(newBoard)
            return onDetermineTurn(newBoard)
        }
        return this
    }
}
