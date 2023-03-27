package domain

class Stones(
    value: List<Stone> = listOf(),
    private val renjuRule: RenjuRuleInterface = RenjuRuleAdapter(RenjuRule())
) {
    private val _value: MutableList<Stone> = value.toMutableList()

    val value: List<Stone>
        get() = _value.toList()

    fun place(stone: Stone): PlaceResult {
        if (!value.none { it.coordinate == stone.coordinate }) {
            println(MESSAGE_CORRUPT_STONE.format(stone.coordinate.x, stone.coordinate.y))
            return PlaceResult.ERROR_ALREADY_PLACE
        }
        _value.add(stone)
        return PlaceResult.SUCCESS
    }

    fun validateRenju(stone: Stone): PlaceResult {
        return when (stone.color) {
            Color.BLACK -> {
                if (renjuRule.isThreeToThree(stone, this) || renjuRule.isFourToFour(
                        stone, this
                    ) || renjuRule.isOverFive(findScore(stone.coordinate, stone.color))
                ) PlaceResult.ERROR_RENJU_RULE
                else PlaceResult.SUCCESS
            }
            Color.WHITE -> {
                PlaceResult.SUCCESS
            }
        }
    }

    fun isWinPlace(): Boolean {
        return findScore(getLastStone().coordinate, getLastStone().color) >= WINNING_CONDITION
    }

    fun getLastStone(): Stone {
        return requireNotNull(this.value.lastOrNull()) { ERROR_STONES_EMPTY }
    }

    private fun findScore(stoneCoordinate: Coordinate, stoneColor: Color): Int {
        return RenjuRule.directions.maxOf { item ->
            val nextCoordinate = (stoneCoordinate + item) ?: return@maxOf 0
            val score = startSearch(nextCoordinate, item, stoneColor, INITIAL_SCORE)
            val invertedDirections = item * -1
            val invertCoordinate = (stoneCoordinate + invertedDirections) ?: return@maxOf 0
            val invertedScore =
                startSearch(
                    invertCoordinate, invertedDirections, stoneColor,
                    INITIAL_SCORE
                )
            score + invertedScore
        }
    }

    private fun startSearch(
        coordinate: Coordinate,
        direction: Point,
        color: Color,
        count: Int
    ): Int {
        if (this.value.any { it.coordinate == coordinate && it.color == color }) {
            val nextCoordinate = (coordinate + direction) ?: return count + SEARCH_INTERVAL
            return startSearch(nextCoordinate, direction, color, count + SEARCH_INTERVAL)
        }
        return count
    }

    fun findStone(x: Int, y: Int): Stone? {
        return this.value.find {
            it.coordinate.x == x
                    && it.coordinate.y == y
        }
    }

    fun clearStones() {
        this._value.clear()
    }

    companion object {
        private const val MESSAGE_CORRUPT_STONE = "같은 위치에 이미 돌이 있습니다. 위치 : (%d, %d)"
        private const val WINNING_CONDITION = 4

        private const val ERROR_STONES_EMPTY = "놓여진 바둑돌이 없습니다"

        private const val INITIAL_SCORE = 0
        private const val SEARCH_INTERVAL = 1
    }
}
