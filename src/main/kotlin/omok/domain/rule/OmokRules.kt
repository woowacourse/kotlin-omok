package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.place.Place
import omok.domain.rule.finder.Direction

interface OmokRules {
    val rules: List<OmokRule>

    fun isProtected(
        place: Place,
        board: OmokBoard,
    ): Boolean {
        return rules.any { it.isProtected(place, board) }
    }

    fun isOmok(
        current: Place,
        board: OmokBoard,
    ): Boolean {
        return Direction.getDirectionPair().any { (d1, d2) ->
            val count1 = search(d1, current, board, target = current)
            val count2 = search(d2, current, board, target = current)
            count1 + count2 - 1 == OMOK_MATCH_COUNT
        }
    }

    private fun search(
        direction: Direction,
        place: Place,
        board: OmokBoard,
        target: Place,
    ): Int {
        if (place::class == target::class) {
            val next = board.goto(place, direction)
            return search(direction, next, board, target) + 1
        }
        return 0
    }

    companion object {
        protected const val OMOK_MATCH_COUNT = 5
    }
}
