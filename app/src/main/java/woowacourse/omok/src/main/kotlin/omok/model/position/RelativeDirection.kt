package woowacourse.omok.src.main.kotlin.omok.model.position

data class RelativeDirection(val relativeRowDirection: Int, val relativeColumnDirection: Int) {
    operator fun unaryMinus() = RelativeDirection(-relativeRowDirection, -relativeColumnDirection)

    companion object {
        fun getHorizontalDirection() = RelativeDirection(0, 1)

        fun getVerticalDirection() = RelativeDirection(1, 0)

        fun getUpwardDirection() = RelativeDirection(1, 1)

        fun getDownwardDirection() = RelativeDirection(1, -1)

        fun getRelativeDirections() = listOf(getHorizontalDirection(), getVerticalDirection(), getUpwardDirection(), getDownwardDirection())
    }
}
