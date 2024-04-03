package woowacourse.omok.db

import android.content.Context
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.OmokStones
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor
import woowacourse.omok.util.mapStoneColorToString

class OmokRepository(context: Context) {
    private val dbDao = OmokEntryDao(context)

    fun findSavedBoard(): Board {
        val omokEntries = dbDao.findAllDatabase()
        return mapEntriesToBoard(omokEntries)
    }

    private fun mapEntriesToBoard(omokEntries: List<OmokEntry>): Board {
        val omokStones =
            OmokStones(
                omokEntries.associate { item ->
                    Position(item.x, item.y) to mapStringToStoneColor(item.color)
                },
            )
        return Board(omokStones)
    }

    private fun mapStringToStoneColor(colorString: String): StoneColor {
        return when (colorString) {
            "흑" -> StoneColor.BLACK
            "백" -> StoneColor.WHITE
            else -> throw IllegalArgumentException()
        }
    }

    fun save(stone: OmokStone) {
        dbDao.save(
            OmokEntry(
                x = stone.position.x,
                y = stone.position.y,
                color = mapStoneColorToString(stone.color),
            ),
        )
    }

    fun delete() {
        dbDao.delete()
    }
}
