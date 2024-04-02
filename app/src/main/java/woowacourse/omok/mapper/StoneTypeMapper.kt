package woowacourse.omok.mapper

import woowacourse.omok.model.board.Stone

object StoneTypeMapper {
    fun toDomainModel(stoneType: Int): Stone? = when (stoneType) {
        1 -> Stone.BLACK
        -1 -> Stone.WHITE
        else -> null
    }
}
