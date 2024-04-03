package woowacourse.omok.domain.model

import woowacourse.omok.db.OmokEntity
import woowacourse.omok.domain.model.OmokGame.Companion.BOARD_SIZE
import woowacourse.omok.domain.model.StoneType.Companion.getStoneTypeByName

class Board {
    val table: List<MutableList<StoneType>> =
        List(BOARD_SIZE) {
            MutableList(BOARD_SIZE) {
                StoneType.EMPTY
            }
        }

    var beforePoint: Point? = null
        private set

    fun initializeTable(omokEntities: List<OmokEntity>) {
        omokEntities.forEach { omokEntity ->
            table[omokEntity.pointY][omokEntity.pointX] =
                getStoneTypeByName(omokEntity.stoneTypeName)
        }
    }

    fun putStoneOnTable(
        point: Point,
        stoneType: StoneType,
    ) {
        table[point.y][point.x] = stoneType
        beforePoint = point
    }

    fun getBoardLine(index: Int): List<StoneType> = table[BOARD_SIZE - index]

    fun getBoardPoint(point: Point): StoneType = table[point.y][point.x]

    fun getLastStoneType(): StoneType {
        return if (beforePoint != null) {
            table[beforePoint!!.y][beforePoint!!.x]
        } else {
            StoneType.EMPTY
        }
    }

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }
}
