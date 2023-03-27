package woowacourse.omok

import android.app.Application
import woowacourse.omok.data.db.entity.Game
import woowacourse.omok.data.db.entity.User

class OmokApplication : Application() {

    companion object {
        var user: User? = null
        lateinit var game: Game
    }
}
