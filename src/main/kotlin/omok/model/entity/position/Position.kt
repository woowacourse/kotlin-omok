package omok.model.entity.position

interface Position {
    val row: GridElement
    val column: GridElement
}

data class DefaultPosition(
    override val row: GridElement,
    override val column: GridElement,
) : Position {
    companion object {
        operator fun invoke(
            row: Int,
            column: Int,
        ) = DefaultPosition(DefaultGridElement(row), DefaultGridElement(column))
    }
}
