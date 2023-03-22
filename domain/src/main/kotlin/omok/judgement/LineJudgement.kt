package omok.judgement

import omok.HorizontalAxis
import omok.Player
import omok.Position
import omok.Stone

class LineJudgement(val player: Player, val position: Position) {
    fun check(): Boolean {
        return checkHorizontal(OMOK_COUNT) || checkVertical(OMOK_COUNT) || checkMajorDiagonal(OMOK_COUNT) || checkSubDiagonal(OMOK_COUNT)
    }

    private fun checkOmok(horizontal: List<Int>, vertical: List<Int>, lineCount: Int = OMOK_COUNT): Boolean {
        var count = 0
        var prev = true
        var present: Boolean
        val expect = player.hand.stones + Stone(position)
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
            if (count == lineCount) return true
        }
        return false
    }

    fun checkHorizontal(lineCount: Int): Boolean {
        return checkOmok((1..15).toList(), List(15) { position.verticalAxis }, lineCount)
    }

    fun checkVertical(lineCount: Int): Boolean {
        return checkOmok(List(15) { position.horizontalAxis.axis }, (1..15).toList(), lineCount)
    }

    fun checkMajorDiagonal(lineCount: Int): Boolean {
        val horizontal = position.horizontalAxis.axis
        val vertical = position.verticalAxis
        if (horizontal <= vertical) {
            return checkOmok((1..15 + horizontal - vertical).toList(), (vertical - horizontal + 1..15).toList(), lineCount)
        }
        return checkOmok((horizontal - vertical + 1..15).toList(), (1..15 - vertical + horizontal).toList(), lineCount)
    }

    fun checkSubDiagonal(lineCount: Int): Boolean {
        val horizontal = position.horizontalAxis.axis
        val vertical = position.verticalAxis
        if (horizontal + vertical > 15) {
            return checkOmok((horizontal + vertical - 15..15).toList().reversed(), (horizontal + vertical - 15..15).toList(), lineCount)
        }
        return checkOmok((1..horizontal + vertical - 1).toList(), (1..horizontal + vertical - 1).toList().reversed(), lineCount)
    }

    companion object {
        private const val OMOK_COUNT = 5
    }
}
