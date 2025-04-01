package omok.domain.place

import omok.domain.board.OmokBoard

class OmokStones(place: List<Place> = listOf()) {
    var places: List<Place> = place.toList()
        get() = field.toList()
        private set

    fun getEmptyStones(): List<Place> {
        return (1..OmokBoard.MAX_ROW_SIZE).flatMap { y ->
            (1..OmokBoard.MAX_COLUMN_SIZE).map { x ->
                getPointAt(x, y)
            }
        }
    }

    fun getPointAt(
        row: Int,
        column: Int,
    ): Place {
        val isValidRow = row in 1..OmokBoard.MAX_ROW_SIZE
        val isValidColumn = column in 1..OmokBoard.MAX_COLUMN_SIZE
        return places.find { it.x == column && it.y == row }
            ?: if (isValidRow && isValidColumn) Empty(column, row) else Protected.dummy()
    }

    fun add(place: Place) {
        require(place !is Empty) { ERROR_EMPTY_ADD_NOT_SUPPORTED }
        require(!isProtected(place)) { ERROR_PROTECTED_POSITION }
        require(!isOccupied(place)) { ERROR_OCCUPIED_POSITION }
        places += place
    }

    fun remove(place: Place) {
        places.find { it.x == place.x && it.y == place.y }?.let { places -= it }
    }

    private fun isOccupied(place: Place): Boolean {
        return getPointAt(place.y, place.x) !is Empty
    }

    private fun isProtected(place: Place): Boolean {
        if (place is White) return false
        return getPointAt(place.y, place.x) is Protected
    }

    companion object {
        private const val ERROR_EMPTY_ADD_NOT_SUPPORTED = "빈 Stone은 넣을 수 없습니다"
        private const val ERROR_OCCUPIED_POSITION = "해당 위치에는 이미 돌이 놓여 있습니다. 다른 위치를 선택하세요."
        private const val ERROR_PROTECTED_POSITION = "해당 위치는 금수 자리입니다. 다른 위치를 선택하세요."
    }
}
