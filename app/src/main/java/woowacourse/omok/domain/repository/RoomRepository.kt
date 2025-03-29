package woowacourse.omok.domain.repository

import woowacourse.omok.domain.room.Room

interface RoomRepository {
    fun save(room: Room): Long

    fun readAll(): List<Room>

    fun delete(roomId: Long)
}
