package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.point.Black
import omok.domain.point.Empty
import omok.domain.point.Point

object DfsRenjuFinder : Finder {
    private fun dfs(
        direction: Direction,
        point: Point,
        board: OmokBoard,
        depth: Int = 0,
    ): SearchResult {
        val next = board.goto(point, direction)
        if (next !is Black && next !is Empty) {
            if (point is Empty) {
                return SearchResult(0, false, isIndirectlyClosed = true)
            }
            return SearchResult(0, true)
        }

        if (point is Black || (point is Empty && depth <= 3)) {
            return if (point is Black || depth == 0) {
                dfs(direction, next, board, depth + 1).let { it.copy(stoneCount = it.stoneCount + 1) }
            } else {
                if (next is Empty) {
                    // 6목 거짓금수
                    if (board.goto(next, direction) is Black) {
                        return SearchResult(0, false, isIndirectlyClosed = true)
                    }
                    return SearchResult(0, false)
                }
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
