package woowacourse.omok.domain.room

class Rooms(private val items: List<Room>) {
    operator fun plus(room: Room): Rooms {
        return Rooms(items + room)
    }

    fun remove(room: Room) {
        items.toMutableList().remove(room)
    }
}
