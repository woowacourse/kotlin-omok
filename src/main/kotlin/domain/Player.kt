package domain

abstract class Player {
    abstract val color: Color
    fun place(stones: Stones, coordinateReader: CoordinateReader): Stone {
        val stone = makeValidateStone(stones, coordinateReader)
        stones.place(stone)
        return stone
    }

    private fun makeValidateStone(stones: Stones, coordinateReader: CoordinateReader): Stone {
        val coordinate = coordinateReader.read(color)
        val stone = Stone(color, coordinate)
        if (!validateRenju(stones, stone)) {
            return makeValidateStone(stones, coordinateReader)
        }
        return stone
    }

    abstract fun validateRenju(stones: Stones, stone: Stone): Boolean
}
