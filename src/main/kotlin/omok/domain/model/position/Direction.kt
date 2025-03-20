package omok.domain.model.position

data class Direction(val rowDelta: Int, val colDelta: Int) {
    fun inverse() = Direction(-rowDelta, -colDelta)
}
