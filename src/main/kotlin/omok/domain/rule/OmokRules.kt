package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.rule.finder.Direction
import omok.domain.stone.Stone

interface OmokRules {
    val rules: List<OmokRule>

    fun isProtected(
        stone: Stone,
        board: OmokBoard,
    ): Boolean {
        return rules.any { it.isProtected(stone, board) }
    }

    fun isOmok(
        current: Stone,
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
        stone: Stone,
        board: OmokBoard,
        target: Stone,
    ): Int {
        if (stone::class == target::class) {
            val next = board.goto(stone, direction)
            return search(direction, next, board, target) + 1
        }
        return 0
    }

    companion object {
        protected const val OMOK_MATCH_COUNT = 5
    }
}
