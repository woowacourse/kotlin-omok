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
            val stoneDrawable = getCurrentStoneDrawable(omokGameData, index)
            placeCurrentStone(stoneDrawable, view)
        }
    }

    private fun getCurrentStoneDrawable(
        omokGameData: Map<Int, OmokEntry>,
        index: Int,
    ): Int? {
        val stoneType = omokGameData[index]?.stoneType
        val stoneDrawable =
            StoneType.entries.firstOrNull { it.type == stoneType }?.let {
                OmokBoardAdapter.convertStoneTypeToDrawable(it)
            }
        return stoneDrawable
    }

    private fun placeCurrentStone(
        stoneDrawable: Int?,
        view: ImageView,
    ) {
        stoneDrawable?.let {
            view.setImageResource(it)
        }
    }
}
