package woowacourse.omok

import android.app.Activity

class DoubleClickBackKeyHandler(private val activity: Activity) {
    private var backPressedTime = 0L

    fun onBackPressed(firstClickAction: () -> Unit, secondClickAction: () -> Unit) {
        if (System.currentTimeMillis() > (backPressedTime + BACK_PRESSED_INTERVAL_TIME)) {
            backPressedTime = System.currentTimeMillis()
            firstClickAction()
            Toaster.showToast(activity, R.string.one_more_click_please_back_key)
        } else if (System.currentTimeMillis() <= (backPressedTime + BACK_PRESSED_INTERVAL_TIME)) {
            secondClickAction()
        }
    }

    companion object {
        private const val BACK_PRESSED_INTERVAL_TIME = 2000L
    }
}
