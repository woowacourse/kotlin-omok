package woowacourse.omok.omokgame

import android.content.Context
import domain.CoordinateState
import domain.domain.state.State
import woowacourse.omok.data.dao.BoardDao
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.dao.UserDao
import woowacourse.omok.home.Room

class OmokGameService(context: Context, roomId: Int) {
    private val boardDao = BoardDao(context)
    private val room by lazy { getRoomInfo(context, roomId) }

    val roomId get() = room.roomId
    val roomTitle get() = room.roomTitle
    val firstUserName get() = room.firstUserEntity.userName
    val secondUserName get() = room.secondUserEntity.userName

    fun readBoard(): State {
        return boardDao.readBoard(roomId)
    }

    fun updateDb(state: State, preColor: CoordinateState) {
        val y = (state.getLastPosition() ?: return).y
        val x = (state.getLastPosition() ?: return).x
        boardDao.insertStone(room.roomId, y, x, preColor)
    }

    fun closeDb() {
        boardDao.closeDb()
    }

    private fun getRoomInfo(context: Context, roomId: Int): Room {
        val roomDao = RoomDao(context)
        val userDao = UserDao(context)

        val roomEntity = roomDao.getRoom(roomId)
        val room = with(roomEntity) {
            Room(roomId, roomTitle, userDao.getUser(firstUserId), userDao.getUser(secondUserId))
        }

        roomDao.closeDb()
        userDao.closeDb()
        return room
    }
}
