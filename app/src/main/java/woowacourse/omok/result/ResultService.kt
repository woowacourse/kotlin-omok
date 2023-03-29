package woowacourse.omok.result

import android.content.Context
import woowacourse.omok.data.dao.RoomDao

class ResultService(context: Context) {
    private val roomDao = RoomDao(context)

    fun deleteRoom(roomId: Int) {
        roomDao.deleteRoom(roomId)
    }

    fun closeDb() {
        roomDao.closeDb()
    }
}
