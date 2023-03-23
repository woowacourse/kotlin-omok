package woowacourse.omok.data

data class Room(
    val title: String = "",
    val player: Player,
    val status: Int = 0,
    val time: Int = 0,
) : RoomListType
