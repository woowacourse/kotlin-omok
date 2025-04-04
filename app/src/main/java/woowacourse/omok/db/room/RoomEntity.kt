package woowacourse.omok.db.room

import woowacourse.omok.domain.room.Room

data class RoomEntity(
    val roomId: Long = 0L,
    val roomName: String,
) {
    fun toDomain(): Room {
        return Room(
            id = this.roomId,
            roomName = this.roomName,
        )
    }
}

fun Room.toEntity(): RoomEntity {
    return RoomEntity(
        roomName = this.roomName,
    )
}
