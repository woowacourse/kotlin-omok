package omok.domain.rule.finder

import omok.domain.board.OmokBoard
import omok.domain.place.Place

interface Finder {
    fun search(
        current: Place,
        board: OmokBoard,
        direction: Direction,
    ): SearchResult

    fun count(
        place: Place,
        board: OmokBoard,
        match: (SearchResult, SearchResult) -> Boolean,
    ): Int {
        return Direction.getDirectionPair().count { (d1, d2) ->
            val searchResult1 = search(place, board, d1)
            val searchResul2 = search(place, board, d2)
            match(searchResult1, searchResul2)
        }
    }
}
