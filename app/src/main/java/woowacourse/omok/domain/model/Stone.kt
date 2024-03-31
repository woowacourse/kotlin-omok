package woowacourse.omok.domain.model

enum class Stone {
    BLACK,
    WHITE,
    NONE,
    ;

    fun nextOrFirst(): Stone = if (this == BLACK) WHITE else BLACK

    companion object {
        fun String?.toStone(): Stone {
            return when (this) {
                "black" -> BLACK
                "white" -> WHITE
                else -> NONE
            }
        }
    }
}
