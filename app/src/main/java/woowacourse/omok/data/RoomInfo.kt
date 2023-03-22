package woowacourse.omok.data

data class RoomInfo(
    val title: String = "",
    val status: String = "",
    val time: String = "",
    val player: PlayerInfo,
) : RoomListType
