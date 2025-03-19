package omok.domain.board

enum class OmokRow(val value: Int) {
    FIFTEEN(15),
    FOURTEEN(14),
    THIRTEEN(13),
    TWELVE(12),
    ELEVEN(11),
    TEN(10),
    NINE(9),
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1),
    WALL(-1),
    ;

    override fun toString(): String {
        return if (value < 10) " ${this.value}" else "${this.value}"
    }

    companion object {
        fun entriesWithoutWall(): List<OmokRow> = OmokRow.entries.filter { it != WALL }.toList()

        fun find(value: Int): OmokRow =
            OmokRow.entries.find { it.value == value }
                ?: WALL
    }
}
