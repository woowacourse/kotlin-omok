package omok.model.rule

data class RulePosition(
    val x: Int,
    val y: Int,
) {
    operator fun plus(direction: RuleDirection): RulePosition = RulePosition(x + direction.x, y + direction.y)
}
