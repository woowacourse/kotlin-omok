package woowacourse.omok.domain.room

import woowacourse.omok.db.room.RoomDaoHandler

class Rooms(private val roomDaoHandler: RoomDaoHandler) {
    private val _roomList: MutableList<Room> = roomDaoHandler.readAll().toMutableList()
    val roomList: List<Room> get() = _roomList.toList()

    fun addRoom(roomName: String): Long {
        val room = Room(roomName = roomName)
        val roomId = roomDaoHandler.save(Room(roomName = roomName))
        _roomList.add(room.copy(id = roomId))
        return roomId
    }

    fun deleteRoom(index: Int) {
        if (index in _roomList.indices) {
            val room = _roomList[index]
            roomDaoHandler.delete(room.id)
            _roomList.removeAt(index)
        }
    }
}
