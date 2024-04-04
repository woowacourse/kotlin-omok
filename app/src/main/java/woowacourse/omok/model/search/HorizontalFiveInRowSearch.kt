package woowacourse.omok.model.search

import woowacourse.omok.model.Color
import woowacourse.omok.model.Rows

class HorizontalFiveInRowSearch(
    status: Rows,
) : FiveInRowSearch(status) {
    override fun search(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ) {
        if (!isVisitedPosition(color, horizontalCoordinate, verticalCoordinate)) return
        search(color, horizontalCoordinate, verticalCoordinate - 1)
        search(color, horizontalCoordinate, verticalCoordinate + 1)
    }
}
