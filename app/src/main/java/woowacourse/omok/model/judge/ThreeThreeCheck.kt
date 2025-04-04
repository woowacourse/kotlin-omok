package model.judge

import woowacourse.omok.model.Direction
import woowacourse.omok.model.Position
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneColor

object ThreeThreeCheck : Rule {
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
                val segmentStart = stone.position.moveOrNull(direction, offset)
                val segmentEnd = segmentStart?.moveOrNull(direction, SEGMENT_LENGTH)
                if (segmentStart == null || segmentEnd == null) continue
                if (stones.find { it.position == segmentStart }?.color != stone.color &&
                    stones.find { it.position == segmentStart }?.color != null
                ) {
                    continue
                }
                if (stones.find { it.position == segmentEnd }?.color != stone.color &&
                    stones.find { it.position == segmentEnd }?.color != null
                ) {
                    continue
                }
                val threeStones = checkFoul(stone, stones, segmentStart, segmentEnd, direction)
                if (threeStones != null) {
                    uniqueSegments.add(threeStones)
                    break
                }
            }
        }
        return uniqueSegments.size > THREE_STONES_COUNT_LIMIT
    }

    override fun checkFoul(
        stone: Stone,
        stones: List<Stone>,
        startPosition: Position,
        lastPosition: Position,
        direction: Direction,
    ): List<Stone>? {
        var pos = direction.nextPosition(startPosition)
        val sameColorStones = mutableListOf<Stone>()
        var blankCount = 0
        while (true) {
            val currentStone = stones.find { it.position == pos }
            if (currentStone?.color == stone.color) {
                sameColorStones.add(currentStone)
            } else if (currentStone == null) {
                blankCount++
            } else {
                return null
            }
            pos = direction.nextPosition(pos)
            if (pos == lastPosition) break
        }
        if (!(sameColorStones.size == REQUIRED_STONES && blankCount == REQUIRED_BLANKS)) return null
        return sameColorStones
    }

    private const val THREE_STONES_COUNT_LIMIT = 1
    private const val REQUIRED_STONES = 3
    private const val REQUIRED_BLANKS = 2
    private const val SEGMENT_LENGTH = 6
    private const val BEFORE_START_STONE = -1
    private const val FIVE_STONES_START_STONE_IS_LAST = -5
    private val FOUR_OFFSET_RANGE = FIVE_STONES_START_STONE_IS_LAST..BEFORE_START_STONE
}
