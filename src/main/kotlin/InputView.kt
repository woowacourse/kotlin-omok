class InputView {

    fun requestPoint(stone: Stone?): Point {
        println("${getNextColorName(stone?.color)}의 차례입니다.${getLatestPoint(stone?.point)}")
        print("위치를 입력하세요: ")
        val input = readln()
        return runCatchingOrNull {
            val x = convertCharToX(input[0])
            val y = input.substring(1).toInt()
            Point(x, y)
        } ?: requestPoint(stone)
    }

    private fun convertCharToX(char: Char): Int = char.code - 64
    private fun convertXtoChar(x: Int): Char = (64 + x).toChar()

    private fun getNextColorName(color: Color?): String = when (color) {
        null, Color.WHITE -> "흑"
        Color.BLACK -> "백"
    }

    private fun getLatestPoint(point: Point?) = when (point) {
        null -> ""
        else -> " (마지막 돌의 위치:${convertXtoChar(point.x)}${point.y})"
    }

    private fun <T> runCatchingOrNull(block: () -> T?): T? {
        return kotlin.runCatching {
            block()
        }.onFailure {
            println(it.message)
        }.getOrNull()
    }
}
