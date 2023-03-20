package domain.turn

import domain.board.PlacedBoard
import domain.judgement.ForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Stone

class WhiteWin(
    board: PlacedBoard,
    previousBoard: PlacedBoard,
    latestStone: Stone,
    winningCondition: WinningCondition,
    forbiddenCondition: ForbiddenCondition
) : FinishedTurn(board) {
    override val curColor: Color = Color.WHITE

    init {
        check(board.size > 1) { ERROR_SIZE }
        check(board.blackSize >= board.whiteSize) { ERROR_WHITE_SIZE_MORE_OR_SAME }
        if (latestStone.color == Color.BLACK) {
            check(forbiddenCondition.isForbidden(previousBoard.getBoards(), latestStone)) { ERROR_BLACK_IS_NOT_FORBIDDEN }
        } else {
            check(winningCondition.isWin(previousBoard.getBoards(), latestStone)) { ERROR_IS_NOT_WIN }
        }
    }

    companion object {
        private const val ERROR_SIZE = "[ERROR] 게임의 승패가 결정나기에 너무 돌의 개수가 적습니다."
        private const val ERROR_WHITE_SIZE_MORE_OR_SAME = "[ERROR] 흑 돌의 개수와 같거나 흑 돌이 한개 더 많아야 흰 돌이 이길 수 있습니다."
        private const val ERROR_BLACK_IS_NOT_FORBIDDEN = "[ERROR] 마지막 돌이 흑 돌인데 금수에 놓지 않았습니다. 백 돌이 이길 수 없습니다."
        private const val ERROR_IS_NOT_WIN = "[ERROR] 백 돌이 아직 이기지 않았습니다."
    }
}
