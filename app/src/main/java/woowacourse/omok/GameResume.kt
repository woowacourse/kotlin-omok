package woowacourse.omok

import android.widget.ImageView
import omok.model.stone.StoneType
import woowacourse.omok.adapter.OmokBoardAdapter
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao

object GameResume {
    fun restoreProgressGameData(
        positions: List<ImageView>,
        dao: OmokEntryDao,
    ) {
        val omokGameData = dao.findAll().associateBy { it.position }

        positions.forEachIndexed { index, view ->
            showInProgressGameData(omokGameData, index, view)
        }
    }

    private fun showInProgressGameData(
        omokGameData: Map<Int, OmokEntry>,
        index: Int,
        view: ImageView,
    ) {
        omokGameData[index]?.let { entry ->
            placeCurrentGameStones(entry, view)
        }
    }

    private fun placeCurrentGameStones(
        entry: OmokEntry,
        view: ImageView,
    ) {
        StoneType.entries.firstOrNull { it.type == entry.stoneType }?.let {
            view.setImageResource(OmokBoardAdapter.convertStoneTypeToDrawable(it))
        }
    }
}
