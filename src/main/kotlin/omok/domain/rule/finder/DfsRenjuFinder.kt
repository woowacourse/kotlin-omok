package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Empty
import omok.domain.place.Place

object DfsRenjuFinder : Finder {
    private fun dfs(
        direction: Direction,
        place: Place,
        board: OmokBoard,
        depth: Int = 0,
    ): SearchResult {
        val next = board.goto(place, direction)
        if (next !is Black && next !is Empty) {
            if (place is Empty) {
                return SearchResult(0, false, isIndirectlyClosed = true)
            }
            return SearchResult(0, true)
        }

        if (place is Black || (place is Empty && depth <= 3)) {
            return if (place is Black || depth == 0) {
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
        current: Place,
        board: OmokBoard,
        direction: Direction,
    ): SearchResult {
        return dfs(direction, current, board)
    }
}
