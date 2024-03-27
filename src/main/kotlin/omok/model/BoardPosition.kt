package omok.model

data class BoardPosition(private val rowCoordinate: BoardCoordinate, private val columnCoordinate: BoardCoordinate) {
    fun getRow(): Int = rowCoordinate.getNumber()

    fun getColumn(): Int = columnCoordinate.getNumber()
}
