package woowacourse.omok

import android.app.Application
import android.content.SharedPreferences

class Omok : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPref = getSharedPreferences("Omok", MODE_PRIVATE)
    }

    companion object {
        lateinit var instance: Omok
            private set
        private lateinit var sharedPref: SharedPreferences

        fun getNickname(): String {
            return sharedPref.getString("nickname", "") ?: ""
        }

        fun editNickname(nickname: String) {
            sharedPref
                .edit()
                .putString("nickname", nickname)
                .apply()
        }
    }
}
