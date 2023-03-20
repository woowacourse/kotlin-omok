package domain.turn

import domain.board.PlacedBoard
import domain.judgement.ForbiddenCondition
import domain.judgement.WinningCondition
import domain.stone.Color
import domain.stone.Stone

class BlackWin(
    board: PlacedBoard,
    previousBoard: PlacedBoard,
    latestStone: Stone,
    winningCondition: WinningCondition,
    forbiddenCondition: ForbiddenCondition
) : FinishedTurn(board) {
    override val curColor: Color = Color.BLACK

    init {
        check(board.size > 1) { ERROR_SIZE }
        check(board.blackSize > board.whiteSize) { ERROR_WHITE_SIZE_MORE_OR_SAME }
        check(latestStone.color == Color.BLACK) { ERROR_LATEST_STONE_IS_NOT_BLACK }
        check(!forbiddenCondition.isForbidden(previousBoard.getBoards(), latestStone)) { ERROR_BLACK_IS_FORBIDDEN }
        check(winningCondition.isWin(previousBoard.getBoards(), latestStone)) { ERROR_IS_NOT_WIN }
    }

    companion object {
        private const val ERROR_SIZE = "[ERROR] 게임의 승패가 결정나기에 너무 돌의 개수가 적습니다."
        private const val ERROR_WHITE_SIZE_MORE_OR_SAME = "[ERROR] 흰 돌보다 개수가 더 많아야 흑 돌이 이길 수 있습니다."
        private const val ERROR_LATEST_STONE_IS_NOT_BLACK = "[ERROR] 마지막 돌은 검은 돌이여야 합니다."
        private const val ERROR_BLACK_IS_FORBIDDEN = "[ERROR] 흑 돌이 금수에 놨습니다. 이길 수 없습니다."
        private const val ERROR_IS_NOT_WIN = "[ERROR] 흑 돌이 아직 이기지 않았습니다."
    }
}
