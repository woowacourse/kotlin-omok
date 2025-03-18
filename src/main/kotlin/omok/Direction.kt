package omok

data class Direction(val rowDelta: Int, val colDelta: Int) {
    operator fun unaryMinus() = Direction(-rowDelta, -colDelta)
}
