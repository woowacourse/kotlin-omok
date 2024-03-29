package woowacourse.omok

class BoardLayout(private val size: Int) {
    private val layout: Array<Array<CoordinateState>> = Array(size) { Array(size) { CoordinateState.Empty } }

    operator fun get(
        x: Int,
        y: Int,
    ): CoordinateState = layout[x][y]

    operator fun set(
        x: Int,
        y: Int,
        value: CoordinateState,
    ) {
        layout[x][y] = value
    }

    fun isEmpty(
        x: Int,
        y: Int,
    ): Boolean = layout[x][y] == CoordinateState.Empty

    fun deepCopy(): Array<Array<CoordinateState>> =
        Array(size) { x ->
            Array(size) { y -> layout[x][y] }
        }
}
