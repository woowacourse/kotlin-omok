package omok.domain

class LineJudgement(val player: Player, val position: Position) {

    fun checkOmok(horizontal: List<Int>, vertical: List<Int>): Boolean {
        var count = 0
        var prev = true
        var present: Boolean
        horizontal.zip(vertical).forEach { axis ->
            present = player.stones.any { stone ->
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

    fun checkHorizontal(): Boolean {
        return checkOmok((1..15).toList(), List(15) { position.verticalAxis })
    }

    fun checkVertical(): Boolean {
        return checkOmok(List(15) { position.horizontalAxis.value }, (1..15).toList())
    }

    fun checkMajorDiagonal(): Boolean {
        val horizontal = position.horizontalAxis.value
        val vertical = position.verticalAxis
        if (horizontal <= vertical) {
            return checkOmok((1..15 + horizontal - vertical).toList(), (vertical - horizontal + 1..15).toList())
        }
        return checkOmok((horizontal - vertical + 1..15).toList(), (1..15 - vertical + horizontal).toList())
    }

    fun checkSubDiagonal(): Boolean {
        val horizontal = position.horizontalAxis.value
        val vertical = position.verticalAxis
        if (horizontal + vertical >= 15) {
            return checkOmok((horizontal + vertical - 15..15).toList().reversed(), (horizontal + vertical - 15..15).toList())
        }
        return checkOmok((1..horizontal + vertical - 1).toList(), (1..horizontal + vertical - 1).toList().reversed())
    }
}
