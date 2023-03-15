package domain

import domain.turn.State

class Referee {

    fun isWin(state: State): Boolean {
        val size = State.BOARD_SIZE
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (!state.value[i][j]) continue

                // 가로 방향 체크
                if (j + 4 < size &&
                    state.value[i][j + 2] &&
                    state.value[i][j + 3] &&
                    state.value[i][j + 1] &&
                    state.value[i][j + 4]
                ) {
                    return true
                }

                // 세로 방향 체크
                if (i + 4 < size &&
                    state.value[i + 1][j] &&
                    state.value[i + 2][j] &&
                    state.value[i + 3][j] &&
                    state.value[i + 4][j]
                ) {
                    return true
                }

                // 대각선 방향 체크 (좌상단에서 우하단)
                if (i + 4 < size && j + 4 < size &&
                    state.value[i + 1][j + 1] &&
                    state.value[i + 2][j + 2] &&
                    state.value[i + 3][j + 3] &&
                    state.value[i + 4][j + 4]
                ) {
                    return true
                }

                // 대각선 방향 체크 (우상단에서 좌하단)
                if (i + 4 < size && j - 4 >= 0 &&
                    state.value[i + 1][j - 1] &&
                    state.value[i + 2][j - 2] &&
                    state.value[i + 3][j - 3] &&
                    state.value[i + 4][j - 4]
                ) {
                    return true
                }
            }
        }
        return false
    }
}
