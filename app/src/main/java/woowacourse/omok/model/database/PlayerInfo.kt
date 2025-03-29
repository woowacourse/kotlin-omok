package woowacourse.omok.model.database

data class PlayerInfo(
    val name: String,
    val playCount: Int,
    val blackWinCount: Int,
    val whiteWinCount: Int,
)
