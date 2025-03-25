package model

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
            for (offset in -5..-1) {
                val segmentStart = stone.position.moveOrNull(direction, offset)
                val segmentEnd = segmentStart?.moveOrNull(direction, 6)
                if (segmentStart == null || segmentEnd == null) continue
                if (stones.find { it.position.isSamePosition(segmentStart) }?.color?.isSameColor(stone.color) == false) {
                    continue
                }
                if (stones.find { it.position.isSamePosition(segmentEnd) }?.color?.isSameColor(stone.color) == false) {
                    continue
                }
                val threeStones = checkFoul(stone, stones, segmentStart, segmentEnd, direction)
                if (threeStones != null) {
                    uniqueSegments.add(threeStones)
                    break
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
        var pos = direction.nextPosition(startPosition)
        val sameColorStones = mutableListOf<Stone>()
        var blankCount = 0
        while (true) {
            val currentStone = stones.find { it.position.isSamePosition(pos) }
            if (currentStone?.color == stone.color) {
                sameColorStones.add(currentStone)
            } else if (currentStone == null) {
                blankCount++
            } else {
                return null
            }
            pos = direction.nextPosition(pos)
            if (pos.isSamePosition(lastPosition)) break
        }
        if (!(sameColorStones.size == 3 && blankCount == 2)) return null
        return sameColorStones
    }
}
