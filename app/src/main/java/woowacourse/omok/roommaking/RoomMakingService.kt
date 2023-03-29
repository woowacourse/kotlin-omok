package woowacourse.omok.roommaking

import android.content.Context
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.dao.UserDao

class RoomMakingService(context: Context) {
    private val userDao = UserDao(context)
    private val roomDao = RoomDao(context)

    fun insertRoom(roomTitle: String, firstUserName: String, secondUserName: String): Int {
        val firstUserId = userDao.insertUser(firstUserName)
        val secondUserId = userDao.insertUser(secondUserName)
        return roomDao.insertRoom(roomTitle, firstUserId, secondUserId)
    }

    fun closeDb() {
        userDao.closeDb()
        roomDao.closeDb()
    }
}
