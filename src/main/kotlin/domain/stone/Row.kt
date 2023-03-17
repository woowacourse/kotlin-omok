package domain.stone

enum class Row(val y: Int) {
    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5),
    SEVEN(6),
    EIGHT(7),
    NINE(8),
    TEN(9),
    ELEVEN(10),
    TWELVE(11),
    THIRTEEN(12),
    FOURTEEN(13),
    FIFTEEN(14);

    fun up(): Row? = values().find { it.y == y + 1 }
    fun down(): Row? = values().find { it.y == y - 1 }

    companion object {
        /**
         * TODO: 에러 메세지 내용이 바뀌어야 할 것 같아요!
         */
        private const val ERROR_ROW_RANGE = "[ERROR] COLUMN의 범위는 1에서 15사이입니다."
        fun valueOf(y: Int): Row = values().find { it.y == y } ?: throw IllegalArgumentException(ERROR_ROW_RANGE)
    }
}
