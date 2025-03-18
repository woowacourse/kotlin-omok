package domain.model

data class Direction(val rowDelta: Int, val colDelta: Int) {
    fun inverse() = Direction(-rowDelta, -colDelta)
}
