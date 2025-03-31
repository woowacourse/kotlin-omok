package woowacourse.omok.db.room

import woowacourse.omok.domain.room.Room

class RoomDaoMapper(private val roomDao: RoomDao) {
    fun save(room: Room): Long {
        return roomDao.insertRoom(room.toEntity())
    }

    fun readAll(): List<Room> {
        return roomDao.getAllRooms().map { it.toDomain() }
    }

    fun delete(roomId: Long) {
        roomDao.deleteRoom(roomId)
    }
}
