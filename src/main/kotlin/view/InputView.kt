package view

import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class InputView {

    private val pointRegex = """^[A-Z]((1)[0-5]|[1-9])""".toRegex()
    private fun Char.toCoordinate(): Int = this.code - CONVERTING_BASE_NUMBER - MATCHING_UNIT

    private fun Int.toCoordinateString(): Char = (CONVERTING_BASE_NUMBER + this).toChar()

    private fun Color?.toColorName(): String = when (this) {
        null, Color.WHITE -> BLACK
        Color.BLACK -> WHITE
    }

    private fun Point?.toLatestPointString() = when (this) {
        null -> EMPTY_STRING
        else -> LAST_STONE_POINT_MESSAGE.format((this.x + MATCHING_UNIT).toCoordinateString(), this.y + MATCHING_UNIT)
    }

    private fun String.matchesOrNull(regex: Regex): String? {
        if (this.matches(regex)) {
            return this
        }
        return null
    }


    // TODO: 에러가 발생했을 때의 예외처리를 View 가 가져가는 것이 적절할까요?
    // 현재와 같은 구조에서 Column 값이 잘못입력되었을 때 추가적인 비즈니스 로직을 처리하고 싶을 경우, view 에서 이미 예외를 처리해버릴 경우 Controller 는 감지할 수 가 없습니다.
    // 예외에는 view 레벨의 예외와 controller 레벨, 도메인 레벨의 예외가 각각 있을텐데 이러한 구분이 없이 모두 view 에서 처리가 되는 것 같아요.
    fun requestPoint2(latestStone: Stone?): Point {
        println(TURN_MESSAGE.format(latestStone?.color.toColorName(), latestStone?.point.toLatestPointString()))
        print(REQUEST_POINT_MESSAGE)

        while (true) {
            readln().matchesOrNull(pointRegex)?.let { input ->
                return Point(
                    x = input[0].toCoordinate(),
                    y = input.substring(1).toInt() - MATCHING_UNIT
                )
            }
            println(ERROR_INPUT_FORM)
        }
    }

    companion object {
        private const val MATCHING_UNIT = 1
        private const val ERROR_INPUT_FORM = "[ERROR] 입력 형태가 올바르지 않습니다."
        private const val TURN_MESSAGE = "%s의 차례입니다. %s"
        private const val REQUEST_POINT_MESSAGE = "위치를 입력하세요: "
        private const val EMPTY_STRING = ""
        private const val LAST_STONE_POINT_MESSAGE = "(마지막 돌의 위치:%c%d)"
        private const val CONVERTING_BASE_NUMBER = 64
        private const val BLACK = "흑"
        private const val WHITE = "백"
    }
}
