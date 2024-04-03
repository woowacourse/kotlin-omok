package woowacourse.omok.model.game

import woowacourse.omok.model.board.Stone

sealed interface PlaceType {
    val stone: Stone

    fun canPlace(): Boolean
}

data object DoubleOpenThreePlace : PlaceType {
    override val stone = Stone.NONE

    override fun canPlace() = false
}

data object DoubleFourPlace : PlaceType {
    override val stone = Stone.NONE

    override fun canPlace() = false
}

data object OverlinePlace : PlaceType {
    override val stone = Stone.NONE

    override fun canPlace() = false
}

data object DuplicationPlace : PlaceType {
    override val stone = Stone.NONE

    override fun canPlace() = false
}

data object BlackStonePlace : PlaceType {
    override val stone: Stone = Stone.BLACK

    override fun canPlace() = true
}

data object WhiteStonePlace : PlaceType {
    override val stone: Stone = Stone.WHITE

    override fun canPlace() = true
}
