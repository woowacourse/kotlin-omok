package woowacourse.omok.game.view

import android.content.Context
import android.widget.Toast
import error.ErrorHandler
import error.OmokError

class AndroidViewErrorHandler(val context: Context) : ErrorHandler {
    override fun log(exception: OmokError) {
        val errorMessage = exception.message
        Toast.makeText(context, "[ERROR] : $errorMessage", Toast.LENGTH_SHORT).show()
    }
}
