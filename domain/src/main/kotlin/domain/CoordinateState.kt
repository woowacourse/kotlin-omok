package domain

enum class CoordinateState {
    BLACK,
    WHITE,
    EMPTY,
    ;

    companion object {
        fun findByOrdinal(num: Int): CoordinateState {
            return values().find { it.ordinal == num }
                ?: throw IllegalArgumentException("잘못된 인자입니다.")
        }
    }
}
