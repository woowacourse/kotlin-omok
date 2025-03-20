package omok.domain

data class Turn(private var turn: StoneType = StoneType.BLACK) {
    var color: StoneType = StoneType.BLACK
        private set

    fun next() {
        turn =
            if (turn == StoneType.BLACK) {
                StoneType.WHITE
            } else {
                StoneType.BLACK
            }
    }

    fun isBlack(): Boolean = if(turn == StoneType.BLACK) true else false

    fun isWhite(): Boolean = if (turn == StoneType.WHITE) true else false

    fun stone(position: String): Stone {
        val regex = """([A-Z]+)(\d+)""".toRegex()
        val matchResult = regex.matchEntire(position) ?: throw IllegalArgumentException(ERROR_NOT_FIND)
        val (rowString, column) = matchResult.destructured
        val row =
            RowType.entries.find { it.name == rowString }?.value ?: throw IllegalArgumentException(ERROR_NOT_FIND)
        val stone = Stone(Position(row, (column.toInt() - 1)), color)
        return stone
    }

    companion object {
        private const val ERROR_NOT_FIND = "유효하지 않은 입력입니다. 다시 입력해주세요."
    }
}