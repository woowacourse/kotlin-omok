package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus
import omok.domain.point.Point

object DfsRenjuFinder : Finder {
    private fun dfs(
        direction: Direction,
        point: Point,
        board: OmokBoard,
        depth: Int = 0,
    ): SearchResult {
        val next = board.goto(point, direction)
        if (next.stoneStatus != StoneStatus.BLACK && next.stoneStatus != StoneStatus.EMPTY) {
            if (point.stoneStatus == StoneStatus.EMPTY) return SearchResult(0, false, true)
            return SearchResult(0, true)
        }

        if (point.stoneStatus == StoneStatus.BLACK || (point.stoneStatus == StoneStatus.EMPTY && depth <= 3)) {
            return if (point.stoneStatus == StoneStatus.BLACK || depth == 0) {
                dfs(direction, next, board, depth + 1).let { it.copy(stoneCount = it.stoneCount + 1) }
            } else {
                if (next.stoneStatus == StoneStatus.EMPTY) return SearchResult(0, false)
                dfs(direction, next, board, depth + 1)
            }
        }

        return SearchResult(0, false)
    }

    override fun search(
        current: Point,
        board: OmokBoard,
        direction: Direction,
    ): SearchResult {
        return dfs(direction, current, board)
    }
}
