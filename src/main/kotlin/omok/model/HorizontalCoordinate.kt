package omok.model

enum class HorizontalCoordinate(val title: String, val value: Int) {
    ONE("1", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    ELEVEN("11", 11),
    TWELVE("12", 12),
    THIRTEEN("13", 13),
    FOURTEEN("14", 14),
    FIFTEEN("15", 15), ;

    companion object {
        fun valueOf(horizontalCoordinate: Int): HorizontalCoordinate? {
            return entries.find {
                it.value == horizontalCoordinate
            }
        }
    }
}
