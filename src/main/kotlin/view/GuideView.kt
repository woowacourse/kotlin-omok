package view

import model.domain.Stone
import view.util.CoordinationWithIndex

class GuideView {
    private var lastLocation: String? = null
    fun requestCoordination(stone: Stone): Pair<Int, Int> {
        print(getStonePrompt(stone) + YOUR_TURN_PROMPT)
        if (lastLocation != null) println(LAST_STONE_PROMPT + lastLocation)
        print(REQUEST_LOCATION_PROMPT)
        val coordination = readLine()

        return runCatching {
            val dot = checkValidation(coordination)
            CoordinationWithIndex.convertCoordination(dot)
        }
            .onSuccess { lastLocation = coordination }
            .onFailure { println("ERROR") }
            .getOrElse { requestCoordination(stone) }
    }

    private fun getStonePrompt(stone: Stone) = when (stone) {
        Stone.BLACK -> BLACK_PROMPT
        Stone.WHITE -> WHITE_PROMPT
        Stone.EMPTY -> ""
    }

    private fun checkValidation(coordination: String?): String {
        requireNotNull(coordination)
        require(coordination.length in 2..3)

        return coordination
    }

    companion object {
        private const val YOUR_TURN_PROMPT = "의 차례입니다. "
        private const val BLACK_PROMPT = "흑"
        private const val WHITE_PROMPT = "백"
        private const val LAST_STONE_PROMPT = "마지막 돌의 위치: "
        private const val REQUEST_LOCATION_PROMPT = "\n돌을 놓을 위치를 입력하세요: "
    }
}
