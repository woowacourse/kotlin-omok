package woowacourse.omok.domain.model

enum class Stone {
    BLACK,
    WHITE,
    NONE,
    ;

    fun nextOrFirst(): Stone = if (this == BLACK) WHITE else BLACK
}
