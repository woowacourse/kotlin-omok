package woowacourse.omok.database.user

data class User(
    val name: String,
    val winCount: Int = 0,
    val loseCount: Int = 0,
    val id: Long = 0L,
)
