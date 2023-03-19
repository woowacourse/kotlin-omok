package domain.turn

import domain.board.PlacedBoard
import domain.judgement.ForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class BlackTurn(
    board: PlacedBoard,
    private val winningCondition: WinningCondition,
    private val forbiddenCondition: ForbiddenCondition
) : RunningTurn(board) {

    override val curColor: Color = Color.BLACK

    override fun nextBoard(newBoard: PlacedBoard, position: Position): Turn {
        return when {
            forbiddenCondition.isForbidden(board.getBoards(), Stone(position, curColor)) ->
                WhiteWin(newBoard)

            winningCondition.isWin(board.getBoards(), Stone(position, curColor)) ->
                BlackWin(newBoard)

            else -> WhiteTurn(newBoard, winningCondition, forbiddenCondition)
        }
    }
}
