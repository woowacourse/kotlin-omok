package woowacourse.omok.model

enum class StoneState {
    BEFORE_PLACED,
    OUTSIDE_THE_BOARD,
    FORBIDDEN,
    OCCUPIED,
    PLACED, ;

    fun checkPlacementSuccess(): Boolean {
        return when (this) {
            BEFORE_PLACED -> false
            OUTSIDE_THE_BOARD -> false
            FORBIDDEN -> false
            OCCUPIED -> false
            PLACED -> true
        }
    }
}
