package woowacourse.omok

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object Storage {
    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences("Omok", Application.MODE_PRIVATE)
    }

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
