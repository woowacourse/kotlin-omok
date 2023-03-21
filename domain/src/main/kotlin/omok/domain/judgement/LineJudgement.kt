package omok.domain.judgement

import omok.domain.HorizontalAxis
import omok.domain.Player
import omok.domain.Position
import omok.domain.Stone

class LineJudgement(val player: Player, val stone: Stone) {
    fun check(): Boolean {
        return checkHorizontal() || checkVertical() || checkMajorDiagonal() || checkSubDiagonal()
    }

    private fun checkOmok(horizontal: List<Int>, vertical: List<Int>): Boolean {
        var count = 0
        var prev = true
        var present: Boolean
        val expect = player.hand.stones + stone
        horizontal.zip(vertical).forEach { axis ->
            present = expect.any { stone ->
                stone.findPosition(
                    Position(HorizontalAxis.getHorizontalAxis(axis.first), axis.second)
                )
            }
            if ((count == 0 && present) || (prev && present))
                count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5) return true
        }
        return false
    }

    private fun checkHorizontal(): Boolean {
        return checkOmok((1..15).toList(), List(15) { stone.getLocation().verticalAxis })
    }

    private fun checkVertical(): Boolean {
        return checkOmok(List(15) { stone.getLocation().horizontalAxis.axis }, (1..15).toList())
    }

    private fun checkMajorDiagonal(): Boolean {
        val horizontal = stone.getLocation().horizontalAxis.axis
        val vertical = stone.getLocation().verticalAxis
        if (horizontal <= vertical) {
            return checkOmok((1..15 + horizontal - vertical).toList(), (vertical - horizontal + 1..15).toList())
        }
        return checkOmok((horizontal - vertical + 1..15).toList(), (1..15 - vertical + horizontal).toList())
    }

    private fun checkSubDiagonal(): Boolean {
        val horizontal = stone.getLocation().horizontalAxis.axis
        val vertical = stone.getLocation().verticalAxis
        if (horizontal + vertical > 15) {
            return checkOmok((horizontal + vertical - 15..15).toList().reversed(), (horizontal + vertical - 15..15).toList())
        }
        return checkOmok((1..horizontal + vertical - 1).toList(), (1..horizontal + vertical - 1).toList().reversed())
    }
}
