package woowacourse.omok.model.search

import woowacourse.omok.model.Color

class AscendingFiveInRowSearch(
    status: Array<Array<Color?>>,
) : FiveInRowSearch(status) {
    override fun search(
        color: Color,
        horizontalCoordinate: Int,
        verticalCoordinate: Int,
    ) {
        if (!isVisitedPosition(color, horizontalCoordinate, verticalCoordinate)) return
        search(color, horizontalCoordinate - 1, verticalCoordinate + 1)
        search(color, horizontalCoordinate + 1, verticalCoordinate - 1)
    }
}
