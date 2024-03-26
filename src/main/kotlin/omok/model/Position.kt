package omok.model

data class Position(
    val horizontalCoordinate: HorizontalCoordinate,
    val verticalCoordinate: VerticalCoordinate,
) {
    companion object {
        fun of(
            horizontalCoordinate: Int,
            verticalCoordinate: Int,
        ): Position {
            return Position(
                horizontalCoordinate = HorizontalCoordinate.valueOf(horizontalCoordinate),
                verticalCoordinate = VerticalCoordinate.valueOf(verticalCoordinate),
            )
        }
    }
}
