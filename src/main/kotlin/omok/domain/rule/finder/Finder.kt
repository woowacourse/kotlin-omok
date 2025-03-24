package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.point.Point2

interface Finder {
    fun search(
        current: Point2,
        board: OmokBoard,
        direction: Direction,
    ): SearchResult

    fun count(
        point: Point2,
        board: OmokBoard,
        match: (SearchResult, SearchResult) -> Boolean,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val searchResult1 = search(point, board, d1)
            val searchResul2 = search(point, board, d2)
            match(searchResult1, searchResul2)
        }
    }
}
