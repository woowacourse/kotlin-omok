package woowacourse.omok

import android.app.Activity

class BackKeyHandler(private val activity: Activity) {
    private var backPressedTime = 0L

    fun onBackPressed(exitProcess: () -> Unit) {
        if (System.currentTimeMillis() > (backPressedTime + 2000)) {
            backPressedTime = System.currentTimeMillis()
            ToastFactory.showToast(activity, R.string.one_more_click_please_back_key)
        } else if (System.currentTimeMillis() <= (backPressedTime + 2000)) {
            exitProcess()
            ToastFactory.cancel()
        }
    }
}
