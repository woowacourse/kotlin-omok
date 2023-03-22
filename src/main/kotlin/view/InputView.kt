package view

import domain.stone.StonePosition

class InputView {

    fun inputStonePosition(): StonePosition {
        print(PROMPT_MASSAGE_POSITION)
        return runCatching {
            val position: Pair<Int, Int> = textToPosition(readln())
            println()
            StonePosition.from(position.first, position.second)
        }.onFailure {
            println(PROMPT_MASSAGE_RETRY_POSITION_)
        }.getOrNull() ?: inputStonePosition()
    }

    private fun textToPosition(value: String): Pair<Int, Int> {
        val x: Int = (value[0] + 1) - 'A'
        val y: Int = value.substring(1).toInt()
        return Pair(x, y)
    }

    companion object {
        private const val PROMPT_MASSAGE_POSITION = "위치를 입력하세요: "
        private const val PROMPT_MASSAGE_RETRY_POSITION_ = "올바른 위치를 입력해주세요."
    }
}
