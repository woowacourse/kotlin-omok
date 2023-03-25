package woowacourse.omok

import android.app.Activity

class BackKeyHandler(private val activity: Activity) {
    private var backPressedTime = 0L

    fun onBackPressed(exitProcess: () -> Unit) {
        if (System.currentTimeMillis() > (backPressedTime + BACK_PRESSED_INTERVAL_TIME)) {
            backPressedTime = System.currentTimeMillis()
            ToastIntegratedManager.showToast(activity, R.string.one_more_click_please_back_key)
        } else if (System.currentTimeMillis() <= (backPressedTime + BACK_PRESSED_INTERVAL_TIME)) {
            exitProcess()
            ToastIntegratedManager.cancel()
        }
    }

    companion object {
        private const val BACK_PRESSED_INTERVAL_TIME = 2000L
    }
}
