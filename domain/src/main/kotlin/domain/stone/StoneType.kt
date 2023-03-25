package domain.stone

enum class StoneType(val number: Int) {
    BLACK(0), WHITE(1), EMPTY(2);

    companion object {
        fun getStoneType(number: Int): StoneType? {
            return StoneType.values().find { it.number == number }
        }
    }
}
