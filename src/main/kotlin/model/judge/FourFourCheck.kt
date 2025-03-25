package model.judge

import model.Direction
import model.Position
import model.Stone
import model.StoneColor

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
            for (offset in -4..0) {
                val segmentStart = stone.position.moveOrNull(direction, offset) ?: continue
                val segmentEnd = segmentStart.moveOrNull(direction, 5) ?: continue
                val fourStones = checkFoul(stone, stones, segmentStart, segmentEnd, direction)
                if (fourStones != null) {
                    uniqueSegments.add(fourStones)
                }
            }
        }
        return uniqueSegments.size >= 2
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
        val firstStone = stones.find { it.position.isSamePosition(pos) }
        val sameColorStones = mutableListOf<Stone>()

        while (true) {
            val currentStone = stones.find { it.position.isSamePosition(pos) }
            if (currentStone?.color == stone.color) {
                sameColorStones.add(currentStone)
            } else if (currentStone == null) {
                blankCount++
            }
            if (pos.isSamePosition(lastPosition)) break
            pos = direction.nextPosition(pos)
            index++
        }

        val lastStone = stones.find { it.position == lastPosition }
        if (sameColorStones.size != 4) return null

        if (firstStone?.color != stone.color && lastStone?.color != stone.color) {
            if (!(firstStone != null && lastStone != null)) return sameColorStones
            return null
        }

        return null
    }
}
