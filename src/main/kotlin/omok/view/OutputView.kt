package omok.view

import omok.model.BlackStonePlayer
import omok.model.Color
import omok.model.WhiteStonePlayer

class OutputView {
    fun showGameStartHeader() {
        println(HEADER_GAME_START)
    }

    fun showBoard(
        blackPlayer: BlackStonePlayer,
        whitePlayer: WhiteStonePlayer,
    ) {
        val board = MutableList(15) { MutableList(43) { "─" } }

        for (i in 0 until 15) {
            for (j in 0 until 43) {
                if (i == 0 && j == 0) {
                    board[i][j] = "└"
                } else if (i == 14 && j == 42) {
                    board[i][j] = "┐"
                } else if (i == 0 && j == 42) {
                    board[i][j] = "┘"
                } else if (i == 14 && j == 0) {
                    board[i][j] = "┌"
                } else if (i == 0 && j % 3 == 0) {
                    board[i][j] = "┴"
                } else if (i == 14 && j % 3 == 0) {
                    board[i][j] = "┬"
                } else if (j == 0) {
                    board[i][j] = "├"
                } else if (j == 42) {
                    board[i][j] = "┤"
                } else if (j % 3 == 0) {
                    board[i][j] = "┼"
                }
            }
        }

        // 검은 돌 위치 설정
        for (stone in blackPlayer.getStones()) {
            board[(stone.point.col)][(stone.point.row) * 3] = "●"
        }

        // 흰 돌 위치 설정
        for (stone in whitePlayer.getStones()) {
            board[(stone.point.col)][(stone.point.row) * 3] = "○"
        }

        for (row in 14 downTo 0) {
            print("${(row + 1).toString().padStart(2)} ")
            for (col in 0 until 43) {
                print(board[row][col])
            }
            println()
        }

        // 바둑판 상단의 알파벳 인덱스 출력
        println(ROW_INDEX)
    }

    fun showGameResult(turn: Color) {
        println("${if (turn == Color.WHITE) "백" else "흑"}의 승리입니다. 축하합니다.")
    }

    companion object {
        private const val HEADER_GAME_START = "오목 게임을 시작합니다"
        private const val ROW_INDEX = "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
    }
}
