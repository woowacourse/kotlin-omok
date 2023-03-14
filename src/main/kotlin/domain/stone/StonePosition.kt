package domain.stone

data class StonePosition private constructor(
    val x: Int,
    val y: Int,
) {
    companion object {

        private const val MINIMUM_POSITION_NUMBER = 1
        private const val MAXIMUM_POSITION_NUMBER = 15

        fun from(x: Int, y: Int): StonePosition? {
            if (x !in MINIMUM_POSITION_NUMBER..MAXIMUM_POSITION_NUMBER) return null
            if (y !in MINIMUM_POSITION_NUMBER..MAXIMUM_POSITION_NUMBER) return null
            return StonePosition(x, y)
        }
    }
}
