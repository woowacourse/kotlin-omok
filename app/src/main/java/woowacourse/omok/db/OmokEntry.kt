package woowacourse.omok.db

data class OmokEntry(
    val stoneType: String,
    val row: Int,
    val column: Int,
    val id: Long = 0L,
)
