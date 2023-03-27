package woowacourse.omok

class DoubleClickBackKeyHandler {
    private var backPressedTime = 0L

    fun onBackPressed(firstClickAction: () -> Unit, secondClickAction: () -> Unit) {
        if (System.currentTimeMillis() > (backPressedTime + BACK_PRESSED_INTERVAL_TIME)) {
            backPressedTime = System.currentTimeMillis()
            firstClickAction()
        } else if (System.currentTimeMillis() <= (backPressedTime + BACK_PRESSED_INTERVAL_TIME)) {
            secondClickAction()
        }
    }

    companion object {
        private const val BACK_PRESSED_INTERVAL_TIME = 2000L
    }
}
