package woowacourse.omok.mapper

import woowacourse.omok.model.board.Stone

object StoneTypeMapper {
    fun toDomainModel(stoneType: Int): Stone = when (stoneType) {
        1 -> Stone.BLACK
        2 -> Stone.WHITE
        else -> Stone.EMPTY
    }

    //canBeUsedLater
    fun toDatabaseModel(stone: Stone): Int = when (stone) {
        Stone.BLACK -> 1
        Stone.WHITE -> 2
        else -> 0
    }
}
