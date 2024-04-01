package woowacourse.omok.game.database

data class OmokEntry(
    val id: Long? = 0L,
    val row: Int,
    val col: Int,
    val color: String,
)
