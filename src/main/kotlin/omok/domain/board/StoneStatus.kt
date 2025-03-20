package omok.domain.board

enum class StoneStatus {
    BLACK,
    WHITE,
    EMPTY,
    PROTECTED,
    ;

    fun toggle(): StoneStatus = if (this == BLACK) WHITE else BLACK
}
