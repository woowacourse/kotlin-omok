package woowacourse.omok.data.repository

import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.db.room.toEntity
import woowacourse.omok.domain.repository.RoomRepository
import woowacourse.omok.domain.room.Room

class RoomRepositoryImpl(private val roomDao: RoomDao) : RoomRepository {
    override fun save(room: Room): Long {
        return roomDao.insertRoom(room.toEntity())
    }

    override fun readAll(): List<Room> {
        return roomDao.getAllRooms().map { it.toDomain() }
    }

    override fun delete(roomId: Long) {
        roomDao.deleteRoom(roomId)
    }
}
