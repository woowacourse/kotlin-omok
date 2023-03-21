package domain.library.position

enum class Row(val y: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELVE(12),
    THIRTEEN(13),
    FOURTEEN(14),
    FIFTEEN(15);

    fun up(): Row? = values().find { it.y == y + 1 }
    fun down(): Row? = values().find { it.y == y - 1 }

    companion object {

        private const val ERROR_ROW_RANGE = "[ERROR] ROW의 범위는 1에서 15사이입니다."
        fun valueOf(y: Int): Row = values().find { it.y == y } ?: throw IllegalArgumentException(ERROR_ROW_RANGE)
    }
}
