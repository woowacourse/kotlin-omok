package omok.domain.omokRule

data class RulePosition(val x: Int, val y: Int) {
    operator fun plus(direction: RuleDirection): RulePosition {
        return RulePosition(x + direction.x, y + direction.y)
    }
}
