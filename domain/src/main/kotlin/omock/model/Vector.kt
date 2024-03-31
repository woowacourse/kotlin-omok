package omock.model

data class Vector(val x: Int, val y: Int) {
    operator fun unaryMinus(): Vector = Vector(-x, -y)

    companion object {
        val FOUR_DIRECTIONS =
            listOf(
                Vector(1, 0),
                Vector(0, 1),
                Vector(1, 1),
                Vector(1, -1),
            )
    }
}
