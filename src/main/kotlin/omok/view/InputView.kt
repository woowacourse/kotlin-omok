package omok.view

import omok.model.stone.Coordinate

object InputView {
    fun getCoordinate(): Coordinate {
        print("위치를 입력하세요: ")

        val input = readln().trim()
        return runCatching {
            Coordinate.createWithMark(input)
        }.getOrElse {
            println(it.message)
            getCoordinate()
        }
    }
}
