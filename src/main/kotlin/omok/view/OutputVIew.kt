package omok.view

private const val COLUMN_STRING = "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"

private const val X = "X"

object OutputVIew {
    fun printStartMessage() {
        println("오목 게임을 시작합니다.")
    }

    fun printBoard(
        board: Array<Array<Int>>,
        forbiddenPositions: List<Pair<Int, Int>>,
    ) {
        val boardForDisplay = initializeBoard()
        for (row in 0 until 15) {
            // Row labels
            print("${row + 1}".padStart(2, ' ') + " ")
            for (col in 0 until 15) {
                if (row to col in forbiddenPositions) {
                    print(X)
                } else if (board[row][col] == 0) {
                    print(boardForDisplay[row][col]) // 격자 모양 출력
                } else {
                    print(if (board[row][col] == 1) "○" else "●") // 1 또는 -1에 대한 돌 모양 출력
                }
                if (col < 14) print("──")
            }
            println()
        }
        println(COLUMN_STRING)
    }

    private fun initializeBoard(): Array<Array<String>> {
        val board = Array(15) { Array(15) { "┼" } } // 게임판 전체를 격자로 초기화
        for (i in 0..14) {
            board[i][0] = "├" // 왼쪽 테두리
            board[i][14] = "┤" // 오른쪽 테두리
            board[0][i] =
                if (i == 0) {
                    "┌"
                } else if (i == 14) {
                    "┐"
                } else {
                    "┬" // 상단 테두리
                }
            board[14][i] =
                if (i == 0) {
                    "└"
                } else if (i == 14) {
                    "┘"
                } else {
                    "┴" // 하단 테두리
                }
        }
        board[0][0] = "┌" // 왼쪽 상단 모서리
        board[0][14] = "┐" // 오른쪽 상단 모서리
        board[14][0] = "└" // 왼쪽 하단 모서리
        board[14][14] = "┘" // 오른쪽 하단 모서리
        board[0].fill("┬", 1, 14) // 상단 가로선
        board[14].fill("┴", 1, 14) // 하단 가로선
        for (row in 1 until 14) {
            board[row][0] = "├" // 왼쪽 세로선
            board[row][14] = "┤" // 오른쪽 세로선
        }
        return board
    }
}
