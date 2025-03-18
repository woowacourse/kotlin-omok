package omok.domain.board

import java.lang.IllegalStateException

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
    ;

    companion object {
        fun find(value: Int): OmokRow =
            OmokRow.entries.find { it.value == value }
                ?: throw IllegalStateException("올바르지 않은 좌표값 입니다")
    }
}
