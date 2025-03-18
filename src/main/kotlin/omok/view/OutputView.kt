package omok.view

class OutputView {
    fun printStartMessage() = println(MESSAGE_GAME_START)

    companion object {
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
    }
}
