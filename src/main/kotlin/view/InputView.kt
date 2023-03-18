package view

import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class InputView {

    // TODO: 에러가 발생했을 때의 예외처리를 View 가 가져가는 것이 적절할까요?
    // 현재와 같은 구조에서 Column 값이 잘못입력되었을 때 추가적인 비즈니스 로직을 처리하고 싶을 경우, view 에서 이미 예외를 처리해버릴 경우 Controller 는 감지할 수 가 없습니다.
    // 예외에는 view 레벨의 예외와 controller 레벨, 도메인 레벨의 예외가 각각 있을텐데 이러한 구분이 없이 모두 view 에서 처리가 되는 것 같아요.
    fun requestPoint(stone: Stone?, isInitialTry: Boolean = true): Point {
        if (!isInitialTry) println(IMPOSSIBLE_PUT_STONE)
        println(TURN_MESSAGE.format(getNextColorName(stone?.color), getLatestPoint(stone?.point)))
        print(REQUEST_POINT_MESSAGE)
        val input = readln()
        return runCatchingOrNull {
            val x = convertCharToX(input[0]) - 1
            val y = input.substring(1).toInt() - 1
            Point(x, y)
        } ?: requestPoint(stone)
    }

    fun requestPoint2(latestStone: Stone?): Point {
        println(TURN_MESSAGE.format(getNextColorName(latestStone?.color), getLatestPoint(latestStone?.point)))
        print(REQUEST_POINT_MESSAGE)
        val input = readln()
        return runCatchingOrNull {
            val x = convertCharToX(input[0]) - 1
            val y = input.substring(1).toInt() - 1
            Point(x, y)
        } ?: requestPoint(latestStone)
    }

    private fun convertCharToX(char: Char): Int = char.code - CONVERTING_BASE_NUMBER
    private fun convertXtoChar(x: Int): Char = (CONVERTING_BASE_NUMBER + x).toChar()

    private fun getNextColorName(curTurnColor: Color?): String = when (curTurnColor) {
        null, Color.WHITE -> BLACK
        Color.BLACK -> WHITE
    }

    private fun getLatestPoint(point: Point?) = when (point) {
        null -> EMPTY_STRING
        else -> LAST_STONE_POINT_MESSAGE.format(convertXtoChar(point.x + 1), point.y + 1)
    }

    private fun <T> runCatchingOrNull(block: () -> T?): T? {
        return kotlin.runCatching {
            block()
        }.onFailure {
            println(it.message)
        }.getOrNull()
    }

    companion object {
        private const val IMPOSSIBLE_PUT_STONE = "[ERROR] 해당 좌표에 놓아진 돌이 있습니다."
        private const val TURN_MESSAGE = "%s의 차례입니다. %s"
        private const val REQUEST_POINT_MESSAGE = "위치를 입력하세요: "
        private const val EMPTY_STRING = ""
        private const val LAST_STONE_POINT_MESSAGE = "(마지막 돌의 위치:%c%d)"
        private const val CONVERTING_BASE_NUMBER = 64
        private const val BLACK = "흑"
        private const val WHITE = "백"
    }
}
