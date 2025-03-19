package view

class ResultView {
    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    companion object {
        private const val GAME_START_MESSAGE = "오목 게임을 시작합니다."
    }
}
