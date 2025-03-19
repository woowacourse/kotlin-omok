package omok.model

import omok.model.board.OmokBoard
import omok.model.board.Position
import omok.model.board.X
import omok.model.board.Y
import omok.model.stone.StoneState

class Omok : Rule {
    override fun findOmok(
        position: Position,
        stone: StoneState,
        board: OmokBoard,
    ): Boolean {
        val x = position.x
        val y = position.y

        // 가로
        var newX = x.point - 4
        var countX = 0
        repeat(8) {
            if (newX in 1..15) {
                if (board.boardState(Position(X(newX), y)) == stone) {
                    countX++
                    if (countX == 5) return true
                } else {
                    countX = 0
                }
            }
            newX++
        }

        // 세로
        var newY = y.point - 4
        var countY = 0
        repeat(8) {
            if (newY in 1..15) {
                if (board.boardState(Position(x, Y(newY))) == stone) {
                    countY++
                    if (countY == 5) return true
                } else {
                    countY = 0
                }
            }
            newY++
        }

        // 대각선
        var newX1 = x.point - 4
        var newY1 = y.point - 4
        var countDiag1 = 0
        repeat(8) {
            if (newX1 in 1..15 && newY1 in 1..15) {
                if (board.boardState(Position(X(newX1), Y(newY1))) == stone) {
                    countDiag1++
                    if (countDiag1 == 5) return true
                } else {
                    countDiag1 = 0
                }
            }
            newX1++
            newY1++
        }

        // 대각선
        var newX2 = x.point - 4
        var newY2 = y.point + 4
        var countDiag2 = 0
        repeat(8) {
            if (newX2 in 1..15 && newY2 in 1..15) {
                if (board.boardState(Position(X(newX2), Y(newY2))) == stone) {
                    countDiag2++
                    if (countDiag2 == 5) return true
                } else {
                    countDiag2 = 0
                }
            }
            newX2++
            newY2--
        }

        return false
    }
}
