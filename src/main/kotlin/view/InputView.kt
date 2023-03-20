package view

import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

object InputView {

    private const val MATCHING_UNIT = 1
    private const val ERROR_INPUT_FORM = "[ERROR] 입력 형태가 올바르지 않습니다."
    private const val TURN_MESSAGE = "%s의 차례입니다. %s"
    private const val REQUEST_POINT_MESSAGE = "위치를 입력하세요: "
    private const val EMPTY_STRING = ""
    private const val LAST_STONE_POINT_MESSAGE = "(마지막 돌의 위치:%c%d)"
    private const val CONVERTING_BASE_NUMBER = 64
    private const val Black = "흑"
    private const val White = "백"

    private val pointRegex = """^[A-Z]((1)[0-5]|[1-9])""".toRegex()

    private fun Char.toCoordinate(): Int = this.code - CONVERTING_BASE_NUMBER - MATCHING_UNIT

    private fun Int.toCoordinateString(): Char = (CONVERTING_BASE_NUMBER + this).toChar()

    private fun Color?.toColorName(): String = when (this) {
        null, Color.White -> Black
        Color.Black -> White
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

    fun requestPoint(latestStone: Stone?): Point {
        println(TURN_MESSAGE.format(latestStone?.color.toColorName(), latestStone?.point.toLatestPointString()))

        while (true) {
            print(REQUEST_POINT_MESSAGE)
            readln().matchesOrNull(pointRegex)?.let { input ->
                return Point(
                    x = input[0].toCoordinate(),
                    y = input.substring(1).toInt() - MATCHING_UNIT
                )
            }
            println(ERROR_INPUT_FORM)
        }
    }
}
