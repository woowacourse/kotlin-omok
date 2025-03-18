import PointState.EMPTY
import PointState.OCCUPIED_BLACK
import PointState.OCCUPIED_WHITE

data class Point(
    val position: Position,
    private var _state: PointState = EMPTY,
) {
    val state: PointState get() = _state

    fun placeStone(stone: Stone) {
        _state =
            when (stone) {
                Stone.BLACK -> OCCUPIED_BLACK
                Stone.WHITE -> OCCUPIED_WHITE
            }
    }
}
