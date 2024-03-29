package woowacourse.omok.view

import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.PositionX
import woowacourse.omok.model.PositionY

object InputView {
    private const val MESSAGE_INPUT_COORDINATE = "위치를 입력하세요: "

    fun inputStoneCoordinate(): Coordinate {
        print(MESSAGE_INPUT_COORDINATE)
        val input = readlnOrNull().orEmpty()
        val result = input.validateCoordinate()

        return result.getOrElse { throwable ->
            println("${throwable.message}")
            inputStoneCoordinate()
        }
    }

    fun String.validateCoordinate(): Result<Coordinate> {
        return runCatching {
            Coordinate(
                PositionX(this.substring(1).toInt()),
                PositionY(this.substring(0, 1).first() - 'A' + 1),
            )
        }
    }
}
