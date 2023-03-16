package domain

import domain.board.Board
import domain.board.OmokBoard

class Referee {

    fun isWin(board: Board, state: State): Boolean {
        val size = Board.BOARD_SIZE
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (board.value[i][j] == State.EMPTY) continue

                // 가로 방향 체크
                if (j + 4 < size &&
                    board.value[i][j] == state &&
                    board.value[i][j + 1] == state &&
                    board.value[i][j + 2] == state &&
                    board.value[i][j + 3] == state &&
                    board.value[i][j + 4] == state
                ) {
                    return true
                }

                // 세로 방향 체크
                if (i + 4 < size &&
                    board.value[i][j] == state &&
                    board.value[i + 1][j] == state &&
                    board.value[i + 2][j] == state &&
                    board.value[i + 3][j] == state &&
                    board.value[i + 4][j] == state
                ) {
                    return true
                }

                // 대각선 방향 체크 (좌상단에서 우하단)
                if (i + 4 < size && j + 4 < size &&
                    board.value[i][j] == state &&
                    board.value[i + 1][j + 1] == state &&
                    board.value[i + 2][j + 2] == state &&
                    board.value[i + 3][j + 3] == state &&
                    board.value[i + 4][j + 4] == state
                ) {
                    return true
                }

                // 대각선 방향 체크 (우상단에서 좌하단)
                if (i + 4 < size && j - 4 >= 0 &&
                    board.value[i][j] == state &&
                    board.value[i + 1][j - 1] == state &&
                    board.value[i + 2][j - 2] == state &&
                    board.value[i + 3][j - 3] == state &&
                    board.value[i + 4][j - 4] == state
                ) {
                    return true
                }
            }
        }
        return false
    }

    fun checkForbidden(myBoard: OmokBoard, stone: Stone): Boolean {
        return OmokRuleAdapter().checkForbidden(myBoard, stone)
    }
}
