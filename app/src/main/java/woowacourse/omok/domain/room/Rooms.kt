package woowacourse.omok.domain.room

import woowacourse.omok.db.room.RoomDaoMapper

class Rooms(private val roomDaoService: RoomDaoMapper) {
    private val _roomList: MutableList<Room> = roomDaoService.readAll().toMutableList()
    val roomList: List<Room> get() = _roomList.toList()

    operator fun plus(roomName: String): Long {
        val room = Room(roomName = roomName)
        val roomId = roomDaoService.save(Room(roomName = roomName))
        _roomList.add(room.copy(id = roomId))
        return roomId
    }

    operator fun minus(index: Int) {
        if (index in _roomList.indices) {
            val room = _roomList[index]
            roomDaoService.delete(room.id)
            _roomList.removeAt(index)
        }
    }
}
