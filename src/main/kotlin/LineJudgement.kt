class LineJudgement(val player: Player, val position: Position) {
    fun checkHorizontal(): Boolean {
        var count = 0
        var prev = true
        var present: Boolean

        HorizontalAxis.values().forEach { horizontal ->
            present = player.stones.any { stone -> stone.findPosition(Position(horizontal, position.verticalAxis)) }
            if ((count == 0 && present) || (prev && present))
                count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5)
                return true
        }
        return false
    }
}
