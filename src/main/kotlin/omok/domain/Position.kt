package omok.domain

data class Position(val row: Int, val column: Int) {
    val color: StoneType = StoneType.BLACK
}
