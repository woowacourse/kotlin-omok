package domain.stone

data class StonePosition(
    val x: Int,
    val y: Int,
) {

    init {
        require(x in MINIMUM_POSITION_NUMBER..MAXIMUM_POSITION_NUMBER)
        require(y in MINIMUM_POSITION_NUMBER..MAXIMUM_POSITION_NUMBER)
    }

    companion object {
        private const val MINIMUM_POSITION_NUMBER = 1
        private const val MAXIMUM_POSITION_NUMBER = 15
    }
}
