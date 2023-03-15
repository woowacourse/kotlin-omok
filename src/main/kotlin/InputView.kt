class InputView {

    fun requestPoint(stone: Stone?): Point {
        println(TURN_MESSAGE.format(getNextColorName(stone?.color), getLatestPoint(stone?.point)))
        print(REQUEST_POINT_MESSAGE)
        val input = readln()
        return runCatchingOrNull {
            val x = convertCharToX(input[0])
            val y = input.substring(1).toInt()
            Point(x, y)
        } ?: requestPoint(stone)
    }

    private fun convertCharToX(char: Char): Int = char.code - CONVERTING_BASE_NUMBER
    private fun convertXtoChar(x: Int): Char = (CONVERTING_BASE_NUMBER + x).toChar()

    private fun getNextColorName(color: Color?): String = when (color) {
        null, Color.WHITE -> BLACK
        Color.BLACK -> WHITE
    }

    private fun getLatestPoint(point: Point?) = when (point) {
        null -> EMPTY_STRING
        else -> LAST_STONE_POINT_MESSAGE.format(convertXtoChar(point.x), point.y)
    }

    private fun <T> runCatchingOrNull(block: () -> T?): T? {
        return kotlin.runCatching {
            block()
        }.onFailure {
            println(it.message)
        }.getOrNull()
    }

    companion object {
        private const val TURN_MESSAGE = "%s의 차례입니다. %s"
        private const val REQUEST_POINT_MESSAGE = "위치를 입력하세요: "
        private const val EMPTY_STRING = ""
        private const val LAST_STONE_POINT_MESSAGE = "(마지막 돌의 위치:%c%d)"
        private const val CONVERTING_BASE_NUMBER = 64
        private const val BLACK = "흑"
        private const val WHITE = "백"
    }
}
