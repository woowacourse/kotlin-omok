package omok.model

enum class HorizontalCoordinate(val value: Int) {
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
    FIFTEEN(15), ;

    companion object {
        fun valueOf(horizontalCoordinate: Int): HorizontalCoordinate? {
            return entries.find {
                it.value == horizontalCoordinate
            }
        }
    }
}
