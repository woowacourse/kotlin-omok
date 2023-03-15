package omok.model

class Coordinate(coordinate: String) {
    val x: Int
    val y: Int

    init {
        val first = coordinate.first().uppercaseChar() - 'A' + 1
        val second = coordinate.substring(1)
        require(first.isWithInRange() && second.isWithinRange()) { "위치를 알파벳숫자 형태로 입력해주세요. (잘못된 값: $coordinate)" }

        x = Board.BOARD_LENGTH - second.toInt() + 1
        y = first
    }

    private fun Int.isWithInRange(): Boolean = this in BOARD_SIZE_RANGE

    private fun String.isWithinRange(): Boolean {
        val number = this.toIntOrNull()
        return number?.isWithInRange() ?: false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinate

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    companion object {
        private val BOARD_SIZE_RANGE: IntRange = 1..Board.BOARD_LENGTH
    }
}
