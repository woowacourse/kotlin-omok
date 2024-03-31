package woowacourse.omok.omokdb

data class OmokEntry(
    val row: String,
    val column: Int,
    val color: String,
    val id: Long = 0L,
)
