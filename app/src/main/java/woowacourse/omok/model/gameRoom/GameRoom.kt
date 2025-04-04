package woowacourse.omok.model.gameRoom

import java.time.LocalDateTime

data class GameRoom(
    val id: Int,
    val blackStonePlayerName: String,
    val whiteStonePlayerName: String,
    var lastPlayTime: LocalDateTime,
)
