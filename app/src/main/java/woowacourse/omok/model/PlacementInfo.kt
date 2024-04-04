package woowacourse.omok.model

class PlacementInfo(
    val status: Rows = Rows(List(COMPUTATION_BOARD_SIZE) { Row(MutableList(COMPUTATION_BOARD_SIZE) { null }) }),
) {
    fun markSinglePlace(
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
        color: Color,
    ) {
        status.values[horizontalCoordinate].placementData[verticalCoordinate] = color
    }

    companion object {
        private const val COMPUTATION_BOARD_SIZE = 16
    }
}
