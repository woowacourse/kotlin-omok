package omok.model.stone

data class GoStone(
    val color: GoStoneColor,
    val coordinate: Coordinate,
) {
    companion object {
        val EMPTY: GoStone = GoStone(GoStoneColor.WHITE, Coordinate(-1, -1))
    }
}
