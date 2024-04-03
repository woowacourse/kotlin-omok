package woowacourse.omok.console.view

class StartView {
    fun print() {
        println(INITIAL_GUIDE_MESSAGE)
    }

    companion object {
        private const val INITIAL_GUIDE_MESSAGE = "오목 게임을 시작합니다."
    }
}
