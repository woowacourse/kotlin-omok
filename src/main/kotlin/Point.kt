import PointState.EMPTY
import PointState.OCCUPIED_BLACK
import PointState.OCCUPIED_WHITE

data class Point(
    val position: Position,
    private var _state: PointState = EMPTY,
) {
    val state: PointState get() = _state

    fun updateState(stoneColor: StoneColor) {
        _state =
            when (stoneColor) {
                StoneColor.BLACK -> OCCUPIED_BLACK
                StoneColor.WHITE -> OCCUPIED_WHITE
            }
    }
}
