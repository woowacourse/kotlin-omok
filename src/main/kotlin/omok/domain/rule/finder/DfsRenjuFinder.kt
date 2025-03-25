package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.stone.Black
import omok.domain.stone.Empty
import omok.domain.stone.Stone

object DfsRenjuFinder : Finder {
    private fun dfs(
        direction: Direction,
        stone: Stone,
        board: OmokBoard,
        depth: Int = 0,
    ): SearchResult {
        val next = board.goto(stone, direction)
        if (next !is Black && next !is Empty) {
            if (stone is Empty) {
                return SearchResult(0, false, isIndirectlyClosed = true)
            }
            return SearchResult(0, true)
        }

        if (stone is Black || (stone is Empty && depth <= 3)) {
            return if (stone is Black || depth == 0) {
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
        current: Stone,
        board: OmokBoard,
        direction: Direction,
    ): SearchResult {
        return dfs(direction, current, board)
    }
}
