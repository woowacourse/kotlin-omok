package woowacourse.omok.domain.model.database

data class OmokTurn(
    val row: Int,
    val column: Int,
    val stoneColor: String,
    val id: Long = 0L,
)
