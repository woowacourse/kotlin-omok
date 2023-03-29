package woowacourse.omok.home

import android.content.Context
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.dao.UserDao

class HomeService(context: Context) {
    private val roomDao = RoomDao(context)
    private val userDao = UserDao(context)

    fun getRooms(): List<Room> {
        return roomDao.getRooms().map {
            Room(
                roomId = it.roomId,
                roomTitle = it.roomTitle,
                firstUserEntity = userDao.getUser(it.firstUserId),
                secondUserEntity = userDao.getUser(it.secondUserId),
            )
        }
    }

    fun closeDb() {
        roomDao.closeDb()
        userDao.closeDb()
    }
}
