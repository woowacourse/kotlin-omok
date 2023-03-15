class InputView {

    fun requestPoint(color: Color, point: Point?, isInitialTry: Boolean = true): Point {
        while (true) {
            println("${getColorName(color)}의 차례입니다.${getLatestPoint(point)}")
            print("위치를 입력하세요: ")
            val input = readln()
            val x = convertCharToX(input[0])
            val y = input.substring(1).toInt()
            kotlin.runCatching { Point(x, y) }
                .onSuccess { return it }
                .onFailure { println(it.message) }
        }
    }

    private fun convertCharToX(char: Char): Int = char.code - 64
    private fun convertXtoChar(x: Int): Char = (64 + x).toChar()

    private fun getColorName(color: Color): String = when (color) {
        Color.BLACK -> "흑"
        Color.WHITE -> "백"
    }

    private fun getLatestPoint(point: Point?) = when (point) {
        null -> ""
        else -> " (마지막 돌의 위치:${convertXtoChar(point.x)}${point.y})"
    }
}
