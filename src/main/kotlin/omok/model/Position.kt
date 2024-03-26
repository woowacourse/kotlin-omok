package omok.model

data class Position(
    val horizontalCoordinate: HorizontalCoordinate,
    val verticalCoordinate: VerticalCoordinate,
) {
    constructor(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ) : this(
        horizontalCoordinate = HorizontalCoordinate.valueOf(horizontalCoordinate),
        verticalCoordinate = VerticalCoordinate.valueOf(verticalCoordinate),
    )
}
