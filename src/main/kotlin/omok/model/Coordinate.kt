package omok.model

class Coordinate(coordinate: String) {
    val x: Int
    val y: Int

    init {
        val first = coordinate.first()
        val second = coordinate.substring(1)
        require(first.isAlphabet() && second.isPositiveNumber()) { "위치를 알파벳숫자 형태로 입력해주세요. (잘못된 값: $coordinate)" }

        x = first.uppercaseChar() - 'A' + 1
        y = second.toInt()
    }

    private fun Char.isAlphabet(): Boolean {
        return this in 'a'..'z' || this in 'A'..'Z'
    }

    private fun String.isPositiveNumber(): Boolean {
        val number = this.toIntOrNull()
        return if (number == null) false else number > 0
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
}
