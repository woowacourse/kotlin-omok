package omok.view

import omok.model.Column
import omok.model.Coordinate
import omok.model.Row

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
                Row.from(this.substring(1)),
                Column.from(this.substring(0, 1)),
            )
        }
    }
}
