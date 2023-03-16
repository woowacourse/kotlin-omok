package domain.rule

import domain.CoordinateState
import domain.Position

object ForbiddenThree {
    fun isForbiddenThree(board: List<List<CoordinateState>>, position: Position): Boolean {
        var openThreeCount = 0
        openThreeCount += findHorizontal(board, position.getX(), position.getY())
        openThreeCount += findDiagonal1(board, position.getX(), position.getY())
        openThreeCount += findVertical(board, position.getX(), position.getY())
        openThreeCount += findDiagonal2(board, position.getX(), position.getY())
        return openThreeCount >= 2
    }

    // ← → 탐색
    // ─ 탐색 : 열린3이 되면 1을 리턴 아니면 0 리턴
    private fun findHorizontal(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        // 열린 3인지 체크하기위한것..
        var blink1 = 1

        // blink2 는 blink1 과 같음 중간에서넣어줄거임.
        // int blink2 = blink1;

        // ←
        var xx = x - 1 // 달라지는 좌표
        var check = false
        left@ while (true) {
            // 좌표끝도달
            if (xx == -1) break@left

            // check를 false로 바꿈으로 두번연속으로 만나는지 확인할수있게.
            if (board[y][xx] == CoordinateState.BLACK) {
                check = false
                stone1++
            }

            // 상대돌을 만나면 탐색중지
            if (board[y][xx] == CoordinateState.WHITE) break@left
            if (board[y][xx] == CoordinateState.EMPTY) {
                // 처음 빈공간을만나 check가 true가 됬는데
                // 연달아 빈공간을만나면 탐색중지
                // 두번연속으로 빈공간만날시 blink카운트를 되돌림.
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break@left
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break@left // 빈공간을만났으나 빈공간을 두번만나면 끝임
                }
            }
            // 계속탐색
            xx--
        }

        // →
        xx = x + 1 // 달라지는 좌표
        var blink2 = blink1 // blink1남은거만큼 blink2,
        if (blink1 == 1) {
            // 빈공간을 만나지않은경우 없었음을기록
            blink1 = 0
        }
        check = false
        right@ while (true) {
            // 좌표끝도달
            if (xx == 15) break@right
            if (board[y][xx] == CoordinateState.BLACK) {
                check = false
                stone2++
            }

            // 상대돌을 만나면 탐색중지
            if (board[y][xx] == CoordinateState.WHITE) break@right
            if (board[y][xx] == CoordinateState.EMPTY) {
                // 두번연속으로 빈공간만날시 blink카운트를 되돌림.
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break@right
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break@right // 빈공간을만났으나 빈공간을 두번만나면 끝임
                }
            }
            xx++
        }
        allStone = stone1 + stone2
        // 삼삼이므로 돌갯수가 2 + 1(현재돌)이아니면 0리턴
        // 이부분이 43을 허용하게해줌. 33만 찾게됨
        if (allStone != 2) {
            return 0
        }
        // 돌갯수가 3이면 열린 3인지 파악.
        val left = stone1 + blink1
        val right = stone2 + blink2

        // 벽으로 막힌경우 - 열린3이 아님
        return if (x - left == 0 || x + right == 15) {
            0
        } else {
            // 상대돌로 막힌경우 - 열린3이 아님
            if (board[y][x - left - 1] == CoordinateState.WHITE || board[y][x + right + 1] == CoordinateState.WHITE) {
                0
            } else {
                1 // 열린3 일때 1 리턴
            }
        }
    }

    // ↖ ↘ 탐색
    private fun findDiagonal1(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1

        // ↖
        var xx = x - 1
        var yy = y - 1
        var check = false
        leftUp@ while (true) {
            if (xx == -1 || yy == -1) break@leftUp
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone1++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break@leftUp
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break@leftUp
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break@leftUp
                }
            }
            xx--
            yy--
        }

        // ↘
        xx = x + 1
        yy = y + 1
        var blink2 = blink1
        if (blink1 == 1) blink1 = 0
        check = false
        rightDown@ while (true) {
            if (xx == 15 || yy == 15) break@rightDown
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone2++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break@rightDown
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break@rightDown
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break@rightDown
                }
            }
            xx++
            yy++
        }
        allStone = stone1 + stone2
        if (allStone != 2) {
            return 0
        }
        val leftUp = stone1 + blink1
        val rightDown = stone2 + blink2
        return if (y - leftUp == 0 || x - leftUp == 0 || y + rightDown == 15 || x + rightDown == 15) {
            0
        } else if (board[y - leftUp - 1][x - leftUp - 1] == CoordinateState.WHITE || board[y + rightDown + 1][x + rightDown + 1] == CoordinateState.WHITE) {
            0
        } else {
            1
        }
    }

    // ↑ ↓ 탐색
    private fun findVertical(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1

        // ↑
        var yy = y - 1
        var check = false
        up@ while (true) {
            if (yy == -1) break@up
            if (board[yy][x] == CoordinateState.BLACK) {
                check = false
                stone1++
            }
            if (board[yy][x] == CoordinateState.WHITE) break@up
            if (board[yy][x] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break@up
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break@up
                }
            }
            yy--
        }

        // ↓
        var blink2 = blink1
        if (blink1 == 1) blink1 = 0
        yy = y + 1
        check = false
        down@ while (true) {
            if (yy == 15) break@down
            if (board[yy][x] == CoordinateState.BLACK) {
                check = false
                stone2++
            }
            if (board[yy][yy] == CoordinateState.WHITE) break@down
            if (board[yy][x] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break@down
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break@down
                }
            }
            yy++
        }
        allStone = stone1 + stone2
        if (allStone != 2) {
            return 0
        }
        val up = stone1 + blink1
        val down = stone2 + blink2
        return if (y - up == 0 || y + down == 15) {
            0
        } else if (board[y - up - 1][x] == CoordinateState.WHITE || board[y + down + 1][x] == CoordinateState.WHITE) {
            0
        } else {
            1
        }
    }

    // ／ 탐색
    // ↙ ↗ 탐색
    private fun findDiagonal2(board: List<List<CoordinateState>>, x: Int, y: Int): Int {
        var stone1 = 0
        var stone2 = 0
        var allStone = 0
        var blink1 = 1

        // ↙
        var xx = x - 1
        var yy = y + 1
        var check = false
        leftDown@ while (true) {
            if (xx == -1 || yy == 15) break@leftDown
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone1++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break@leftDown
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink1++
                    break@leftDown
                }
                if (blink1 == 1) {
                    blink1--
                } else {
                    break@leftDown
                }
            }
            xx--
            yy++
        }

        // ↗
        var blink2 = blink1
        if (blink1 == 1) blink1 = 0
        xx = x + 1
        yy = y - 1
        check = false
        rightUp@ while (true) {
            if (xx == 15 || yy == -1) break@rightUp
            if (board[yy][xx] == CoordinateState.BLACK) {
                check = false
                stone2++
            }
            if (board[yy][xx] == CoordinateState.WHITE) break@rightUp
            if (board[yy][xx] == CoordinateState.EMPTY) {
                check = if (check == false) {
                    true
                } else {
                    blink2++
                    break@rightUp
                }
                if (blink2 == 1) {
                    blink2--
                } else {
                    break@rightUp
                }
            }
            xx++
            yy--
        }
        allStone = stone1 + stone2
        if (allStone != 2) {
            return 0
        }
        val leftDown = stone1 + blink1
        val rightUp = stone2 + blink2
        return if (x - leftDown == 0 || y - rightUp == 0 || y + leftDown == 15 || x + rightUp == 15) {
            0
        } else if (board[y + leftDown + 1][x - leftDown - 1] == CoordinateState.WHITE || board[y - rightUp - 1][x + rightUp + 1] == CoordinateState.WHITE) {
            0
        } else {
            1
        }
    }
}
