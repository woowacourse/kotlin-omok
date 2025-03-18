class Stone(
    val position: Position,
    val color: StoneColor,
) {
    fun isSamePosition(stone: Stone): Boolean {
        return position.isSamePosition(stone.position)
    }
}
