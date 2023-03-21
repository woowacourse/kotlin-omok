package domain.library.self

import domain.CoordinateState
import domain.Position

object ExceedFive {
    fun isExceedFive(
        board: List<List<CoordinateState>>,
        position: Position,
        coordinateState: CoordinateState,
    ): Boolean {
        var fourStone = 0
        fourStone += fourOrOverHorizontal(board, position.x, position.y, coordinateState)
        fourStone += fourOrOverDiagonalDown(board, position.x, position.y, coordinateState)
        fourStone += fourOrOverVertical(board, position.x, position.y, coordinateState)
        fourStone += fourOrOverDiagonalUp(board, position.x, position.y, coordinateState)
        return fourStone >= 1
    }

    private fun fourOrOverHorizontal(
        board: List<List<CoordinateState>>,
        x: Int,
        y: Int,
        targetCoordinateState: CoordinateState,
    ): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1
        val nonTargetCoordinateState =
            if (targetCoordinateState == CoordinateState.BLACK) CoordinateState.WHITE else CoordinateState.BLACK

        // ←  탐색
        var yy = y
        var xx = x - 1
        var check = false
        while (true) {
            if (xx == -1) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone1++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break
                }
            }
            xx--
        }

        // → 탐색
        xx = x + 1
        yy = y
        var blink2 = blink1
        check = false
        while (true) {
            if (xx == 15) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone2++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break
                }
            }
            xx++
        }
        allStone = stone1 + stone2

        // 사사찾는 트리거

        // 현재놓은돌 +1 +5 => 6목이상은 장목. 여기서 놓은돌기준 두방향모두 돌이있어야 장목
        return if (allStone >= 5 && stone1 != 0 && stone2 != 0) 1 else 0
    }

    // ↖ ↘ 탐색
    private fun fourOrOverDiagonalDown(
        board: List<List<CoordinateState>>,
        x: Int,
        y: Int,
        targetCoordinateState: CoordinateState,
    ): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1
        val nonTargetCoordinateState =
            if (targetCoordinateState == CoordinateState.BLACK) CoordinateState.WHITE else CoordinateState.BLACK

        // ↖  탐색
        var yy = y - 1
        var xx = x - 1
        var check = false
        while (true) {
            if (xx == -1 || yy == -1) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone1++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break
                }
            }
            xx--
            yy--
        }

        // ↘  탐색
        yy = y + 1
        xx = x + 1
        check = false
        var blink2 = blink1
        while (true) {
            if (xx == 15 || yy == 15) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone2++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break
                }
            }
            xx++
            yy++
        }
        allStone = stone1 + stone2

        return if (allStone >= 5 && stone1 != 0 && stone2 != 0) 1 else 0
    }

    // ↑ ↓ 탐색
    private fun fourOrOverVertical(
        board: List<List<CoordinateState>>,
        x: Int,
        y: Int,
        targetCoordinateState: CoordinateState,
    ): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1
        val nonTargetCoordinateState =
            if (targetCoordinateState == CoordinateState.BLACK) CoordinateState.WHITE else CoordinateState.BLACK

        // ↑  탐색
        var yy = y - 1
        var xx = x
        var check = false
        while (true) {
            if (yy == -1) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone1++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break
                }
            }
            yy--
        }

        // ↓  탐색
        yy = y + 1
        xx = x
        check = false
        var blink2 = blink1
        while (true) {
            if (yy == 15) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone2++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break
                }
            }
            yy++
        }
        allStone = stone1 + stone2

        return if (allStone >= 5 && stone1 != 0 && stone2 != 0) 1 else 0
    }

    private fun fourOrOverDiagonalUp(
        board: List<List<CoordinateState>>,
        x: Int,
        y: Int,
        targetCoordinateState: CoordinateState,
    ): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1
        val nonTargetCoordinateState =
            if (targetCoordinateState == CoordinateState.BLACK) CoordinateState.WHITE else CoordinateState.BLACK

        var yy = y - 1
        var xx = x + 1
        var check = false
        while (true) {
            if (xx == 15 || yy == -1) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone1++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break
                }
            }
            xx++
            yy--
        }

        // ↙ 탐색
        yy = y + 1
        xx = x - 1
        check = false
        var blink2 = blink1
        while (true) {
            if (xx == -1 || yy == 15) break
            if (board[yy][xx] == targetCoordinateState) {
                check = false
                stone2++
            }
            if (board[yy][xx] == nonTargetCoordinateState) break
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break
                }
            }
            xx--
            yy++
        }
        allStone = stone1 + stone2

        return if (allStone >= 5 && stone1 != 0 && stone2 != 0) 1 else 0
    }
}
