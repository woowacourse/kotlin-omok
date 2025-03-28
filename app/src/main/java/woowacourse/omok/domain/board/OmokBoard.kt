package omok.domain.board

import omok.domain.place.Empty
import omok.domain.place.OmokStones
import omok.domain.place.Place
import omok.domain.place.Protected
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.Direction

class OmokBoard(
    val omokStones: OmokStones,
    val omokRules: OmokRules,
) {
    init {
        require(MAX_ROW_SIZE <= COLUMN_POOL.size) { ERROR_OUT_OF_COLUMN_POOL }
    }

    var latestPlace: Place = Empty.dummy()
        private set

    fun isNotFull() = omokStones.places.size != MAX_COLUMN_SIZE * MAX_ROW_SIZE

    fun addStone(place: Place) {
        omokStones.add(place)
        latestPlace = place
        updateProtectedPlace()
    }

    fun goto(
        currentPosition: Place,
        direction: Direction,
    ): Place {
        val newX = currentPosition.x + direction.x
        val newY = currentPosition.y + direction.y
        return omokStones.getPointAt(newY, newX)
    }

    private fun updateProtectedPlace() {
        omokStones.places
            .filterIsInstance<Protected>()
            .forEach { point ->
                if (!omokRules.isProtected(point, this)) {
                    omokStones.remove(point)
                }
            }
        omokStones.getEmptyStones()
            .forEach { point ->
                if (omokRules.isProtected(point, this)) {
                    omokStones.add(Protected(point.x, point.y))
                }
            }
    }

    companion object {
        // A~Z, a~z 총 최대 52줄의 열 생성이 가능합니다
        val COLUMN_POOL = (('A'..'Z') + ('a'..'z'))
        const val MAX_COLUMN_SIZE = 15
        const val MAX_ROW_SIZE = 15
        private const val ERROR_OUT_OF_COLUMN_POOL = "문자열 풀의 사이즈보다 열의 크기가 큽니다"
    }
}
