package domain.turn

import domain.board.PlacedBoard
import domain.judgement.ForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class WhiteTurn(
    board: PlacedBoard,
    latestStone: Stone,
    private val winningCondition: WinningCondition,
    private val forbiddenCondition: ForbiddenCondition
) : RunningTurn(board) {

    override val curColor: Color = Color.WHITE

    init {
        check(latestStone.color == Color.BLACK) {
            ERROR_LATEST_STONE_IS_NOT_BLACK
        }
    }

    override fun nextBoard(newBoard: PlacedBoard, position: Position): Turn {
        val latestStone = Stone(position, curColor)
        return when {
            winningCondition.isWin(board.getBoards(), latestStone) ->
                WhiteWin(newBoard, board, latestStone, winningCondition, forbiddenCondition)

            else -> BlackTurn(newBoard, board, latestStone, winningCondition, forbiddenCondition)
        }
    }

    companion object {
        private const val ERROR_LATEST_STONE_IS_NOT_BLACK = "[ERROR] 마지막 돌이 검은 돌이 아니므로 현재는 흰 색 돌 차례가 아닙니다."
    }
}
