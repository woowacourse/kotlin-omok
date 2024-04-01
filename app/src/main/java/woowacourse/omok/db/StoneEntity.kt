package woowacourse.omok.db

data class StoneEntity(
    val id: Long = 0L,
    val color: String,
    val row: Int,
    val column: Int,
    val order: Int,
)
