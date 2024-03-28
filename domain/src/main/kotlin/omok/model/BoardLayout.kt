package omok.model

class BoardLayout(private val size: Int) {
    private val layout: Array<Array<PositionType>> = Array(size) { Array(size) { PositionType.EMPTY } }

    operator fun get(
        x: Int,
        y: Int,
    ): PositionType = layout[x][y]

    operator fun set(
        x: Int,
        y: Int,
        value: PositionType,
    ) {
        layout[x][y] = value
    }

    fun isEmpty(
        x: Int,
        y: Int,
    ): Boolean = layout[x][y] == PositionType.EMPTY

    fun deepCopy(): Array<Array<PositionType>> =
        Array(size) { x ->
            Array(size) { y -> layout[x][y] }
        }
}
