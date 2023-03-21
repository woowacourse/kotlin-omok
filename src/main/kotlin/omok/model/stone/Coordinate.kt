package omok.model.stone

data class Coordinate(val x: Int, val y: Int) {

    companion object {
        private const val MINIMUM = 1

        fun createWithMark(mark: String): Coordinate {
            val first = mark.first().uppercaseChar() - 'A' + 1
            val second = mark.substring(1)
            require(first >= MINIMUM && second.isWithinRange()) { "위치를 알파벳숫자 형태로 입력해주세요. (잘못된 값: $mark)" }

            return Coordinate(first, second.toInt())
        }

        private fun String.isWithinRange(): Boolean {
            val number = this.toIntOrNull() ?: return false
            return number >= MINIMUM
        }
    }
}
