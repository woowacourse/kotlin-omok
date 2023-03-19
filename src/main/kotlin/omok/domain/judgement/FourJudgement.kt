package omok.domain.judgement

import omok.domain.BlackStone
import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position

class FourJudgement(private val player: Player, private val otherPlayer: Player, private val position: Position) {
    fun check(): Boolean {
        var cnt = 0
        if (checkHorizontal(position.horizontalAxis.axis - 4, position.horizontalAxis.axis - 3)) cnt++
        if (checkHorizontal(position.horizontalAxis.axis - 1, position.horizontalAxis.axis)) cnt++
        if (checkVertical(position.verticalAxis - 4, position.verticalAxis - 3)) cnt++
        if (checkVertical(position.verticalAxis - 1, position.verticalAxis)) cnt++
        if (checkMajorDiagonal(position.verticalAxis - 4, position.verticalAxis - 3, position.horizontalAxis.axis - 4, position.horizontalAxis.axis - 3)) cnt++
        if (checkMajorDiagonal(position.verticalAxis - 1, position.verticalAxis, position.horizontalAxis.axis - 1, position.horizontalAxis.axis)) cnt++
        if (checkSubDiagonal(position.verticalAxis - 4, position.verticalAxis - 3, position.horizontalAxis.axis - 4, position.horizontalAxis.axis - 3)) cnt++
        if (checkSubDiagonal(position.verticalAxis - 1, position.verticalAxis, position.horizontalAxis.axis - 1, position.horizontalAxis.axis)) cnt++
        return cnt >= 2
    }

    private fun checkFour(horizontal: List<Int>, vertical: List<Int>): Boolean {
        val expect = player.hand.stones + BlackStone(position)
        var count = 0
        var other: Boolean
        var present: Boolean

        horizontal.zip(vertical).forEach { axis ->
            // 백인지 판단
            other = otherPlayer.hand.stones.any { stone ->
                stone.findPosition(
                    Position(HorizontalAxis.getHorizontalAxis(axis.first), axis.second)
                )
            }
            // 흑인지 판단
            present = expect.any { stone ->
                stone.findPosition(
                    Position(HorizontalAxis.getHorizontalAxis(axis.first), axis.second)
                )
            }
            if (present) count++
            else if (other) count = 0

            if (count == 4) return true
        }
        return false
    }

    private fun checkVertical(verticalLower: Int, verticalUpper: Int): Boolean {
        var flag = false

        (verticalLower..verticalUpper).forEach { vertical ->
            if (vertical >= 1 && vertical + 4 <= 15)
                flag = checkFour(List(5) { position.horizontalAxis.axis }, (vertical..vertical + 4).toList())
            if (flag) return true
        }
        return false
    }

    private fun checkHorizontal(horizontalLower: Int, horizontalUpper: Int): Boolean {
        var flag = false

        (horizontalLower..horizontalUpper).forEach { horizontal ->
            if (horizontal >= 1 && horizontal + 4 <= 15)
                flag = checkFour((horizontal..horizontal + 4).toList(), List(5) { position.verticalAxis })
            if (flag) return true
        }
        return false
    }

    private fun checkMajorDiagonal(verticalLower: Int, verticalUpper: Int, horizontalLower: Int, horizontalUpper: Int): Boolean {
        val verticalAxis = (verticalLower..verticalUpper).toList()
        val horizontalAxis = (horizontalLower..horizontalUpper).toList()
        var flag = false

        horizontalAxis.zip(verticalAxis).forEach { axis ->
            if (axis.first >= 1 && axis.first + 4 <= 15 && axis.second >= 1 && axis.second + 4 <= 15) {
                flag = checkFour((axis.first..axis.first + 4).toList(), (axis.second..axis.second + 4).toList())
            }
            if (flag) return true
        }
        return false
    }

    private fun checkSubDiagonal(verticalLower: Int, verticalUpper: Int, horizontalLower: Int, horizontalUpper: Int): Boolean {
        val verticalAxis = (verticalLower..verticalUpper).toList().reversed()
        val horizontalAxis = (horizontalLower..horizontalUpper).toList()
        var flag = false

        horizontalAxis.zip(verticalAxis).forEach { axis ->
            if (axis.first >= 1 && axis.first + 4 <= 15 && axis.second - 4 >= 1 && axis.second <= 15) {
                flag =
                    checkFour((axis.first..axis.first + 4).toList(), (axis.second - 4..axis.second).toList().reversed())
            }
            if (flag) return true
        }
        return false
    }
}
