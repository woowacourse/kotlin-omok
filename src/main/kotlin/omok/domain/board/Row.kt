package omok.domain.board

internal fun String?.toRow(): Row? = Row.values().find { this?.toIntOrNull() == it.axis + 1 }

enum class Row(val axis: Int) {
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

    fun up(): Row? = values().find { it.axis == axis + 1 }
    fun down(): Row? = values().find { it.axis == axis - 1 }
}
