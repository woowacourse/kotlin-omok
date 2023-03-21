package view

import rule.wrapper.point.Point

object InputView {

    fun inputStonePosition(): Point {
        print("위치를 입력하세요: ")
        return runCatching {
            val parsePosition: Pair<Int, Int> = parseXAndY(readln())
            println()
            Point(parsePosition.first, parsePosition.second)
        }.onFailure {
            println("올바른 위치를 입력해주세요.")
        }.getOrNull() ?: inputStonePosition()
    }

    private fun parseXAndY(value: String): Pair<Int, Int> {
        return Pair((value.trim()[0] + 1) - 'A', value.substring(1).toInt())
    }
}
