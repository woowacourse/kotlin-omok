package omok.model.search

import omok.model.Color

class AscendingDfs(
    status: Array<Array<Color?>>,
) : Dfs(status) {
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
