import PointState.EMPTY

data class Point(
    val position: Position,
    val state: PointState = EMPTY,
)
