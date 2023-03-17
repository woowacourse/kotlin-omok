package domain

class Referee {
    fun isWin(omokBoard: OmokBoard, state: State): Boolean {
        val size = OmokBoard.BOARD_SIZE
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (omokBoard.value[i][j] == State.EMPTY) continue

                // 가로 방향 체크
                if (isFiveWidth(omokBoard, i, j, state)) return true
                // 세로 방향 체크
                if (isFiveHeight(omokBoard, i, j, state)) return true
                // 대각선 방향 체크 (좌상단에서 우하단)
                if (isFiveRightDiagonal(omokBoard, i, j, state)) return true
                // 대각선 방향 체크 (우상단에서 좌하단)
                if (isFiveLeftDiagonal(omokBoard, i, j, state)) return true
            }
        }
        return false
    }

    private fun isFiveWidth(omokBoard: OmokBoard, x: Int, y: Int, state: State): Boolean {
        val size = OmokBoard.BOARD_SIZE
        return y + 4 < size &&
            omokBoard.value[x][y] == state &&
            omokBoard.value[x][y + 1] == state &&
            omokBoard.value[x][y + 2] == state &&
            omokBoard.value[x][y + 3] == state &&
            omokBoard.value[x][y + 4] == state
    }

    private fun isFiveHeight(omokBoard: OmokBoard, x: Int, y: Int, state: State): Boolean {
        val size = OmokBoard.BOARD_SIZE
        return x + 4 < size &&
            omokBoard.value[x][y] == state &&
            omokBoard.value[x + 1][y] == state &&
            omokBoard.value[x + 2][y] == state &&
            omokBoard.value[x + 3][y] == state &&
            omokBoard.value[x + 4][y] == state
    }

    private fun isFiveRightDiagonal(omokBoard: OmokBoard, x: Int, y: Int, state: State): Boolean {
        val size = OmokBoard.BOARD_SIZE
        return x + 4 < size && y + 4 < size &&
            omokBoard.value[x][y] == state &&
            omokBoard.value[x + 1][y + 1] == state &&
            omokBoard.value[x + 2][y + 2] == state &&
            omokBoard.value[x + 3][y + 3] == state &&
            omokBoard.value[x + 4][y + 4] == state
    }

    private fun isFiveLeftDiagonal(omokBoard: OmokBoard, x: Int, y: Int, state: State): Boolean {
        val size = OmokBoard.BOARD_SIZE
        return x + 4 < size && y - 4 >= 0 &&
            omokBoard.value[x][y] == state &&
            omokBoard.value[x + 1][y - 1] == state &&
            omokBoard.value[x + 2][y - 2] == state &&
            omokBoard.value[x + 3][y - 3] == state &&
            omokBoard.value[x + 4][y - 4] == state
    }

    fun isMovable(myBoard: OmokBoard, stone: Stone): Boolean {
        return OmokRuleAdapter().checkForbidden(myBoard, stone)
    }
}
