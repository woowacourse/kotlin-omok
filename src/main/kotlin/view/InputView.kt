package view

import domain.stone.StonePosition

object InputView {

    fun inputStonePosition(): StonePosition {
        print("위치를 입력하세요: ")
        return runCatching {
            val parsePosition: Pair<Int, Int> = parseXAndY(readln())
            println()
            StonePosition.from(parsePosition.first, parsePosition.second)
        }.onFailure {
            println("올바른 위치를 입력해주세요.")
        }.getOrNull() ?: inputStonePosition()
    }

    private fun parseXAndY(value: String): Pair<Int, Int> {
        return Pair((value[0] + 1) - 'A', value.substring(1).toInt())
    }
}
