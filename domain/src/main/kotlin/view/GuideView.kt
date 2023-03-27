package view

import model.domain.tools.Stone
import view.util.CoordinationWithIndex

object GuideView {
    private const val ERROR_MESSAGE = "[ERROR] 다시 입력해주세요."
    private const val START = "오목게임을 시작합니다."
    private const val YOUR_TURN_PROMPT = "돌의 차례입니다. "
    private const val BLACK_PROMPT = "흑"
    private const val WHITE_PROMPT = "백"
    private const val LAST_STONE_PROMPT = "마지막 돌의 위치: "
    private const val REQUEST_LOCATION_PROMPT = "\n돌을 놓을 위치를 입력하세요: "
    private const val WIN = "돌이 승리했습니다. "
    private const val MINIMUM = 2
    private const val MAXIMUM = 3
    private val range = MINIMUM..MAXIMUM
    private const val BLANK = ""

    private var lastLocation: String? = null

    fun printStart() = println(START)
    fun requestCoordination(): Pair<Int, Int> {
        val coordination = readLine()

        return runCatching {
            val dot = checkValidation(coordination)
            CoordinationWithIndex.convertCoordination(dot)
        }
            .onSuccess { lastLocation = coordination }
            .onFailure { println(ERROR_MESSAGE) }
            .getOrElse { requestCoordination() }
    }

    fun printRequestCoordination(stone: Stone) {
        print(getStonePrompt(stone) + YOUR_TURN_PROMPT)
        getLastStone()
        print(REQUEST_LOCATION_PROMPT)
    }

    private fun getLastStone() {
        if (lastLocation != null) print(LAST_STONE_PROMPT + lastLocation)
    }

    private fun getStonePrompt(stone: Stone) = when (stone) {
        Stone.BLACK -> BLACK_PROMPT
        Stone.WHITE -> WHITE_PROMPT
        Stone.EMPTY -> BLANK
    }

    private fun checkValidation(coordination: String?): String {
        requireNotNull(coordination)
        require(coordination.length in range)

        return coordination
    }

    fun printWinner(stone: Stone) {
        println(getStonePrompt(stone) + WIN)
    }
}
