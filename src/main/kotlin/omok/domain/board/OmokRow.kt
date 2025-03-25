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

    companion object {
        fun entriesWithoutWall(): List<OmokRow> = OmokRow.entries.filter { it != WALL }.toList()

        fun find(value: Int): OmokRow =
            OmokRow.entries.find { it.value == value }
                ?: WALL

        fun of(value: String): OmokRow {
            val pos = value.toIntOrNull() ?: throw IllegalArgumentException(INVALID_NUMERIC)
            return OmokRow.entries.find { it.value == pos }
                ?: throw IllegalArgumentException(INVALID_ROW)
        }

        private const val INVALID_NUMERIC = "좌표 번호는 숫자로 입력해주세요"
        private const val INVALID_ROW = "잘못된 행 번호입니다. 다시 입력해주세요"
    }
}
