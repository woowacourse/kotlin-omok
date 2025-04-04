package model.judge

import woowacourse.omok.model.Direction
import woowacourse.omok.model.Position
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneColor

object FourFourCheck : Rule {
    override fun checkFoulByAllDirections(
        stone: Stone,
        stones: List<Stone>,
    ): Boolean {
        if (stone.color == StoneColor.WHITE) return false

        val directions =
            listOf(
                Direction.UP,
                Direction.RIGHT,
                Direction.UP_LEFT,
                Direction.DOWN_LEFT,
            )
        val uniqueSegments = mutableSetOf<List<Stone>>()

        for (direction in directions) {
            for (offset in FOUR_OFFSET_RANGE) {
                val segmentStart = stone.position.moveOrNull(direction, offset) ?: continue
                val segmentEnd = segmentStart.moveOrNull(direction, SEGMENT_LENGTH) ?: continue
                val fourStones = checkFoul(stone, stones, segmentStart, segmentEnd, direction)
                if (fourStones != null) {
                    uniqueSegments.add(fourStones)
                }
            }
        }
        return uniqueSegments.size > FOUR_STONES_COUNT_LIMIT
    }

    override fun checkFoul(
        stone: Stone,
        stones: List<Stone>,
        startPosition: Position,
        lastPosition: Position,
        direction: Direction,
    ): List<Stone>? {
        var pos = startPosition
        var blankCount = 0
        var index = 0
        val firstStone = stones.find { it.position == pos }
        val sameColorStones = mutableListOf<Stone>()

        while (true) {
            val currentStone = stones.find { it.position == pos }
            if (currentStone?.color == stone.color) {
                sameColorStones.add(currentStone)
            } else if (currentStone == null) {
                blankCount++
            }
            if (pos == lastPosition) break
            pos = direction.nextPosition(pos)
            index++
        }

        val lastStone = stones.find { it.position == lastPosition }
        if (sameColorStones.size != REQUIRED_FOUR_STONES) return null

        if ((firstStone == null || firstStone.color == stone.color) && (lastStone == null || lastStone.color == stone.color)) {
            if (!(firstStone != null && lastStone != null)) return sameColorStones
            return null
        }

        return null
    }

    private const val FOUR_STONES_COUNT_LIMIT = 1
    private const val REQUIRED_FOUR_STONES = 4
    private const val SEGMENT_LENGTH = 5
    private val FOUR_OFFSET_RANGE = -REQUIRED_FOUR_STONES..0
}
