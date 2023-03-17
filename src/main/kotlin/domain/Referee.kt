package domain

class Referee {

    fun isWin(omokBoard: OmokBoard, state: State): Boolean {
        val size = OmokBoard.BOARD_SIZE
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (omokBoard.value[i][j] == State.EMPTY) continue

                // 가로 방향 체크
                if (j + 4 < size &&
                    omokBoard.value[i][j] == state &&
                    omokBoard.value[i][j + 1] == state &&
                    omokBoard.value[i][j + 2] == state &&
                    omokBoard.value[i][j + 3] == state &&
                    omokBoard.value[i][j + 4] == state
                ) {
                    return true
                }

                // 세로 방향 체크
                if (i + 4 < size &&
                    omokBoard.value[i][j] == state &&
                    omokBoard.value[i + 1][j] == state &&
                    omokBoard.value[i + 2][j] == state &&
                    omokBoard.value[i + 3][j] == state &&
                    omokBoard.value[i + 4][j] == state
                ) {
                    return true
                }

                // 대각선 방향 체크 (좌상단에서 우하단)
                if (i + 4 < size && j + 4 < size &&
                    omokBoard.value[i][j] == state &&
                    omokBoard.value[i + 1][j + 1] == state &&
                    omokBoard.value[i + 2][j + 2] == state &&
                    omokBoard.value[i + 3][j + 3] == state &&
                    omokBoard.value[i + 4][j + 4] == state
                ) {
                    return true
                }

                // 대각선 방향 체크 (우상단에서 좌하단)
                if (i + 4 < size && j - 4 >= 0 &&
                    omokBoard.value[i][j] == state &&
                    omokBoard.value[i + 1][j - 1] == state &&
                    omokBoard.value[i + 2][j - 2] == state &&
                    omokBoard.value[i + 3][j - 3] == state &&
                    omokBoard.value[i + 4][j - 4] == state
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
