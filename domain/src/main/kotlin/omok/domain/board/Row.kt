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

    fun up(): Row? = values().getOrNull(axis + 1)
    fun down(): Row? = values().getOrNull(axis - 1)

    companion object {
        fun toRow(axis: Int): Row = values()[axis]
    }
}
