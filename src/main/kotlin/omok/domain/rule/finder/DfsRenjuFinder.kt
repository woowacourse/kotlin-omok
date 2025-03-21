package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
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
        val previous = board.goto(point, direction.reverse())
        if (point.stoneStatus == StoneStatus.BLACK || (point.stoneStatus == StoneStatus.EMPTY && depth <= 3)) {
            return if (point.stoneStatus == StoneStatus.BLACK || depth == 0) {
                dfs(direction, next, board, depth + 1).let { it.copy(stoneCount = it.stoneCount + 1) }
            } else {
                if (next.x == OmokColumn.WALL || next.y == OmokRow.WALL) return SearchResult(0, false)
                dfs(direction, next, board, depth + 1)
            }
        }

        // 33 거짓금수 중 6목 금수, -> 근데 추후 거짓 금수 판별 시 double dfs로 이 부분을 지울 수 있을 것 같습니다
        if (previous.stoneStatus == StoneStatus.EMPTY && next.stoneStatus == StoneStatus.BLACK) {
            return SearchResult(0, false, isIndirectlyClosed = true)
        }

        return if (point.stoneStatus == StoneStatus.EMPTY &&
            point.x != OmokColumn.WALL &&
            point.y != OmokRow.WALL
        ) {
            SearchResult(0, false)
        } else if (point.stoneStatus == StoneStatus.WHITE && previous.stoneStatus == StoneStatus.EMPTY) {
            SearchResult(0, false, isIndirectlyClosed = true)
        } else {
            SearchResult(0, true)
        }
    }

    override fun search(
        current: Point,
        board: OmokBoard,
        direction: Direction,
    ): SearchResult {
        return dfs(direction, current, board)
    }
}
