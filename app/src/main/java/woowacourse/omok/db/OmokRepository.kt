package woowacourse.omok.db

import android.content.Context
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor
import woowacourse.omok.util.mapStoneColorToString

class OmokRepository(context: Context) {
    private val dbDao = OmokEntryDao(context)

    fun restoreSavedGame(restoreModelAndView: (Board) -> Unit) {
        dbDao.findAllDatabase().takeIf { it.isNotEmpty() }?.let { omokEntries ->
            val savedBoard = mapEntriesToBoard(omokEntries)
            restoreModelAndView(savedBoard)
        }
    }

    private fun mapEntriesToBoard(omokEntries: List<OmokEntry>): Board {
        return Board(
            omokEntries.map { item ->
                OmokStone(
                    position = Position(item.x, item.y),
                    color = mapStringToStoneColor(item.color),
                )
            }.associateBy(OmokStone::position),
        )
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
