package woowacourse.omok.ioc

import android.widget.TableLayout
import omok.domain.board.OmokBoard
import omok.domain.place.OmokStones
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.event.GameEventListener
import omok.event.OmokEventListener
import woowacourse.omok.dao.OmokDao
import woowacourse.omok.dao.OmokDaoImpl
import woowacourse.omok.dao.OmokDbHelper
import woowacourse.omok.view.OmokView

class Container(layout: TableLayout) {
    val omokDbHelper = OmokDbHelper(layout.context)
    val omokDao: OmokDao = OmokDaoImpl(omokDbHelper)
    val omokRules =
        object : OmokRules {
            override val rules = listOf(RenjuRule(DfsRenjuFinder))
        }
    val omokBoard: OmokBoard
        get() = OmokBoard(OmokStones(), omokRules)
    val omokView = OmokView(layout)
    val event: GameEventListener = OmokEventListener(omokView)
}
