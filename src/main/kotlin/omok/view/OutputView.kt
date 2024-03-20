package omok.view

import omok.model.BlackStonePlayer
import omok.model.WhiteStonePlayer

class OutputView {
    fun printBoard(
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
            board[(stone.point.col - 1)][(stone.point.row - 1) * 3] = "●"
        }

        // 흰 돌 위치 설정
        for (stone in whitePlayer.getStones()) {
            board[(stone.point.col - 1)][(stone.point.row - 1) * 3] = "○"
        }

        for (row in 14 downTo 0) {
            print("${(row + 1).toString().padStart(2)} ")
            for (col in 0 until 43) {
                print(board[row][col])
            }
            println()
        }

        // 바둑판 상단의 알파벳 인덱스 출력
        println("   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
    }
}
