package domain.library

import domain.CoordinateState
import domain.Position

object ForbiddenFour {
    fun isForbiddenFour(board: List<List<CoordinateState>>, position: Position): Boolean {
        var fourStone = 0
        fourStone += fourORjang1(board, position.x, position.y)
        fourStone += fourORjang2(board, position.x, position.y)
        fourStone += fourORjang3(board, position.x, position.y)
        fourStone += fourORjang4(board, position.x, position.y)
        return fourStone >= 2
    }

    private fun fourORjang1(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1

        // ←  탐색
        var yy = y
        var xx = x - 1
        var check = false
        while (true) {
            if (xx == -1) break
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone1++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone2++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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

        return if (allStone != 3) {
            0 // 놓은돌제외 3개아니면 4가아니니까.
        } else {
            1 // 놓은돌제외 3개면 4임. 닫히고 열린지는 상관없음.
        }
    }

    // ↖ ↘ 탐색
    private fun fourORjang2(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1

        // ↖  탐색
        var yy = y - 1
        var xx = x - 1
        var check = false
        while (true) {
            if (xx == -1 || yy == -1) break
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone1++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone2++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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

        return if (allStone != 3) 0 else 1
    }

    // ↑ ↓ 탐색
    private fun fourORjang3(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1

        // ↑  탐색
        var yy = y - 1
        var xx = x
        var check = false
        while (true) {
            if (yy == -1) break
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone1++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone2++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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

        return if (allStone != 3) 0 else 1
    }

    private fun fourORjang4(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1

        var yy = y - 1
        var xx = x + 1
        var check = false
        while (true) {
            if (xx == 15 || yy == -1) break
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone1++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone2++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break
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

        return if (allStone != 3) 0 else 1
    }
}
