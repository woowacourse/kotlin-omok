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

    fun initializeTable(omokEntities: List<OmokEntity>) {
        omokEntities.forEach { omokEntity ->
            table[omokEntity.pointY][omokEntity.pointX] =
                getStoneTypeByName(omokEntity.stoneType)
        }
    }

    fun putStone(
        point: Point,
        turn: Turn,
        ruleAdapter: RuleAdapter,
    ): Turn {
        val nextTurn = turn.nextTurn(point, ruleAdapter)
        if (turn != nextTurn) {
            table[point.y][point.x] = turn.stoneType
        }
        return nextTurn
    }

    fun getBoardLine(index: Int): List<StoneType> = table[BOARD_SIZE - index]

    fun getBoardPoint(point: Point): StoneType = table[point.y][point.x]

    operator fun contains(point: Point): Boolean {
        return table[point.y][point.x] != StoneType.EMPTY
    }
}
