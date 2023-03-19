package domain.board

import domain.judgement.ForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class WhiteTurn(
    board: PlacedBoard,
    private val winningCondition: WinningCondition,
    private val forbiddenCondition: ForbiddenCondition
) : RunningTurn(board) {

    override val curColor: Color = Color.WHITE

    override fun nextBoard(newBoard: PlacedBoard, position: Position): Turn {
        return when {
            winningCondition.isWin(board.getBoards(), Stone(position, curColor)) ->
                WhiteWin(newBoard)

            else -> BlackTurn(newBoard, winningCondition, forbiddenCondition)
        }
    }
}
