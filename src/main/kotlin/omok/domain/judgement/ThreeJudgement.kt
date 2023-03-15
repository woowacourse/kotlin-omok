package omok.domain.judgement

import omok.domain.BlackStone
import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position

class ThreeJudgement(private val player: Player, private val otherPlayer: Player, private val position: Position) {
    fun check(): Boolean {
        var cnt = 0
        if (checkHorizontal()) cnt ++
        if (checkVertical()) cnt++
        if (checkMajorDiagonal()) cnt++
        if (checkSubDiagonal()) cnt++
        return cnt >= 2
    }

    private fun checkThree(horizontal: List<Int>, vertical: List<Int>): Boolean {
        val expect = player.stones + BlackStone(position)
        var count = 0
        var other: Boolean
        var present: Boolean

        horizontal.zip(vertical).forEach { axis ->
            // 백인지 판단
            other = otherPlayer.stones.any { stone ->
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

            if (present)
                count++
            else if (other) {
                count = 0
            }

            if (count == 3) return true
        }
        return false
    }

    fun checkVertical(): Boolean {
        val verticalLower = position.verticalAxis - 3
        val verticalUpper = position.verticalAxis + 3
        var flag = false

        (verticalLower..verticalUpper).forEach { vertical ->
            if (vertical >= 1 && vertical + 3 <= 15)
                flag = checkThree(List(4) { position.horizontalAxis.value }, (vertical..vertical + 3).toList())
            if (flag)
                return true
        }
        return false
    }

    fun checkHorizontal(): Boolean {
        val horizontalLower = position.horizontalAxis.value - 3
        val horizontalUpper = position.horizontalAxis.value + 3
        var flag = false

        (horizontalLower..horizontalUpper).forEach { horizontal ->
            if (horizontal >= 1 && horizontal + 3 <= 15)
                flag = checkThree((horizontal..horizontal + 3).toList(), List(4) { position.verticalAxis },)
            if (flag)
                return true
        }
        return false
    }

    fun checkMajorDiagonal(): Boolean {
        val verticalLower = position.verticalAxis - 3
        val verticalUpper = position.verticalAxis + 3
        val horizontalLower = position.horizontalAxis.value - 3
        val horizontalUpper = position.horizontalAxis.value + 3
        val verticalAxis = (verticalLower..verticalUpper).toList()
        val horizontalAxis = (horizontalLower..horizontalUpper).toList()
        var flag = false

        horizontalAxis.zip(verticalAxis).forEach { axis ->
            if (axis.first >= 1 && axis.first + 3 <= 15 && axis.second >= 1 && axis.second + 3 <= 15) {
                flag = checkThree((axis.first..axis.first + 3).toList(), (axis.second..axis.second + 3).toList())
            }
            if (flag)
                return true
        }
        return false
    }

    fun checkSubDiagonal(): Boolean {
        val verticalLower = position.verticalAxis - 3
        val verticalUpper = position.verticalAxis + 3
        val horizontalLower = position.horizontalAxis.value - 3
        val horizontalUpper = position.horizontalAxis.value + 3
        val verticalAxis = (verticalLower..verticalUpper).toList().reversed()
        val horizontalAxis = (horizontalLower..horizontalUpper).toList()
        var flag = false

        horizontalAxis.zip(verticalAxis).forEach { axis ->
            if (axis.first >= 1 && axis.first + 3 <= 15 && axis.second - 3 >= 1 && axis.second <= 15) {
                flag = checkThree((axis.first..axis.first + 3).toList(), (axis.second - 3..axis.second).toList().reversed())
            }
            if (flag)
                return true
        }
        return false
    }
}
