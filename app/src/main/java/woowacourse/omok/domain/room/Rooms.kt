package woowacourse.omok.domain.room

import woowacourse.omok.domain.repository.RoomRepository

class Rooms(private val roomRepository: RoomRepository) {
    private val _roomList: MutableList<Room> = roomRepository.readAll().toMutableList()
    val roomList: List<Room> get() = _roomList.toList()

    operator fun plus(roomName: String): Long {
        val room = Room(roomName = roomName)
        val roomId = roomRepository.save(Room(roomName = roomName))
        _roomList.add(room.copy(id = roomId))
        return roomId
    }

    operator fun minus(index: Int) {
        if (index in _roomList.indices) {
            val room = _roomList[index]
            roomRepository.delete(room.id)
            _roomList.removeAt(index)
        }
    }
}
