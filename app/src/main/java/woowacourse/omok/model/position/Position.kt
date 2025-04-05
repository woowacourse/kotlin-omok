package woowacourse.omok.model.position

data class Position(
    val row: GridElement,
    val column: GridElement,
) {
    companion object {
        operator fun invoke(
            row: Int,
            column: Int,
        ) = Position(GridElement(row), GridElement(column))
    }
}
