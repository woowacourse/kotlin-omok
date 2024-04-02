package woowacourse.omok.model.database

data class Placement(
    val placementId: Long = 0L,
    val gameId: Long,
    val color: String?,
    val horizontalCoordinate: Int,
    val verticalCoordinate: Int,
)
