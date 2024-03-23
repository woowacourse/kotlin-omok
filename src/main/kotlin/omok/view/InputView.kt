package omok.view

import omok.model.PositionY
import omok.model.Coordinate
import omok.model.PositionX

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
                PositionY.from(this.substring(0, 1)),
            )
        }
    }
}
