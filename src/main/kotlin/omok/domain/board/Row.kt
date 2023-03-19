package omok.domain.board

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

    fun up(): Row? = runCatching { values()[axis + 1] }.getOrNull()
    fun down(): Row? = runCatching { values()[axis - 1] }.getOrNull()

    companion object {
        fun toRow(axis: Int): Row = values()[axis - 1]
    }
}
