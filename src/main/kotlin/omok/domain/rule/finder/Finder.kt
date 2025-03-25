package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.stone.Stone

interface Finder {
    fun search(
        current: Stone,
        board: OmokBoard,
        direction: Direction,
    ): SearchResult

    fun count(
        stone: Stone,
        board: OmokBoard,
        match: (SearchResult, SearchResult) -> Boolean,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val searchResult1 = search(stone, board, d1)
            val searchResul2 = search(stone, board, d2)
            match(searchResult1, searchResul2)
        }
    }
}
