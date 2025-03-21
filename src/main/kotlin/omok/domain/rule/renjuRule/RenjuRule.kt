package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.domain.rule.OmokRule

object RenjuRule : OmokRule {
    override fun isProtected(
        point: Point,
        board: OmokBoard,
    ): Boolean {
        return ThreeThreeRule.isProtected(point, board) ||
            FourFourRule.isProtected(point, board) ||
            SixMokRule.isProtected(point, board)
    }

    private fun search(
        direction: Direction,
        point: Point,
        board: OmokBoard,
        depth: Int = 0,
    ): SearchResult {
        val next = board.goto(point, direction)
        val previous = board.goto(point, direction.reverse())
        if (point.stoneStatus == StoneStatus.BLACK || (point.stoneStatus == StoneStatus.EMPTY && depth <= 3)) {
            return if (point.stoneStatus == StoneStatus.BLACK || depth == 0) {
                search(direction, next, board, depth + 1).let { it.copy(stoneCount = it.stoneCount + 1) }
            } else {
                if (next.x == OmokColumn.WALL || next.y == OmokRow.WALL) return SearchResult(0, false)
                search(direction, next, board, depth + 1)
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

    fun searchAllDirection(
        current: Point,
        board: OmokBoard,
        rule: (SearchResult, SearchResult) -> Boolean,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val searchResult1 = search(d1, current, board)
            val searchResul2 = search(d2, current, board)
            rule(searchResult1, searchResul2)
        }
    }
}
