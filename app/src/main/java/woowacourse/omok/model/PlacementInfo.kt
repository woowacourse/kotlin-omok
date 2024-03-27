package omok.model

class PlacementInfo(
    val status: Array<Array<Color?>> = Array(COMPUTATION_BOARD_SIZE) { Array(COMPUTATION_BOARD_SIZE) { null } },
) {
    fun markSinglePlace(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        color: Color,
    ) {
        status[horizontalCoordinate][verticalCoordinate] = color
    }

    companion object {
        private const val COMPUTATION_BOARD_SIZE = 16
    }
}
