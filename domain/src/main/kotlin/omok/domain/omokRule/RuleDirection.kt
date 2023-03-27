package omok.domain.omokRule

data class RuleDirection(val x: Int, val y: Int) {
    fun opposite(): RuleDirection = RuleDirection(-x, -y)

    companion object {
        val DIRECTIONS = listOf(
            RuleDirection(1, 0),
            RuleDirection(0, 1),
            RuleDirection(1, 1),
            RuleDirection(1, -1),
        )
    }
}
