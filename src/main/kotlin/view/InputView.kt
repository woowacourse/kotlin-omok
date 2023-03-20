package view

import domain.stone.StonePosition

class InputView {

    fun inputStonePosition(): StonePosition {
        print("위치를 입력하세요: ")
        return runCatching {
            val position: Pair<Int, Int> = textToPosition(readln())
            println()
            StonePosition.from(position.first, position.second)
        }.onFailure {
            println("올바른 위치를 입력해주세요.")
        }.getOrNull() ?: inputStonePosition()
    }

    private fun textToPosition(value: String): Pair<Int, Int> {
        return Pair((value[0] + 1) - 'A', value.substring(1).toInt())
    }
}
