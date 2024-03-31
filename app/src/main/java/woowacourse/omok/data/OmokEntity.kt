package woowacourse.omok.data

data class OmokEntity(
    val row: Int,
    val col: Int,
    val stone: String,
    val id: Long = 0L,
)
