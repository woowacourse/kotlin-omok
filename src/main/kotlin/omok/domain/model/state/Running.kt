package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.rule.OmokRule
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType

sealed class Running(
    board: Board,
    private val rule: OmokRule,
) : OmokState(board) {
    protected tailrec fun placeStone(
        stoneType: StoneType,
        onPlace: () -> Position,
        onTurn: (Board) -> Running,
    ): OmokState {
        val position = onPlace()
        val omokStone = OmokStone(position, stoneType)
        if (rule.canPlace(omokStone, board)) {
            val newBoard = board.placeStone(position, stoneType)
            if (newBoard.checkWin()) return Finish(newBoard, stoneType)
            return onTurn(newBoard)
        }
        return placeStone(stoneType, onPlace, onTurn)
    }
}
