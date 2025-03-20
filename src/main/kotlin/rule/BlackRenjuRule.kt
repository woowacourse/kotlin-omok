package rule

import Stone
import rule.type.Foul
import rule.type.Violation
import rule.type.Violation.Companion.FOUL_CONDITION_SIZE
import rule.type.Violation.Companion.MAX_EMPTY_SIZE
import rule.type.Violation.Companion.OVERLINE_SIZE
import rule.type.WhiteBlocked
import rule.wrapper.direction.Direction
import rule.wrapper.position.Position

class BlackRenjuRule(
    boardWidth: Int = DEFAULT_BOARD_WIDTH,
    boardHeight: Int = DEFAULT_BOARD_HEIGHT,
) : OmokRule(boardWidth, boardHeight) {
    override fun checkDoubleFoul(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation = checkFoulByAllDirections(blackPositions, whitePositions, startPosition, foul)

    override fun checkOverline(
        stonesPositions: List<Stone>,
        startPosition: Position,
    ): Violation {
        if (checkSerialSameStonesBiDirection(stonesPositions, startPosition, OVERLINE_SIZE)) return Violation.OVERLINE
        return Violation.NONE
    }

    private fun checkFoulByAllDirections(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
        foul: Foul,
    ): Violation {
        var continuousStones = 0
        val dirIterator = Direction.iterator()

        while (dirIterator.hasNext()) {
            val forwardDir = dirIterator.next()
            val backDir = dirIterator.next()

            val (forwardCount, forwardEmptyCount) =
                findStraight(
                    blackPositions,
                    whitePositions,
                    startPosition,
                    forwardDir,
                    foul,
                )
            val (backCount, backEmptyCount) =
                findStraight(
                    blackPositions,
                    whitePositions,
                    startPosition,
                    backDir,
                    foul,
                )
            val totalStoneCount = forwardCount + backCount - 1
            val totalEmptyCount = forwardEmptyCount + backEmptyCount

            when (foul) {
                Foul.DOUBLE_THREE -> {
                    if (totalStoneCount == foul.size && totalEmptyCount <= MAX_EMPTY_SIZE) {
                        val blockedStatus = isBlockedByWhiteStoneInSix(whitePositions, startPosition, forwardDir)
                        if (blockedStatus == WhiteBlocked.NON_BLOCK) continuousStones++
                        if (continuousStones == FOUL_CONDITION_SIZE) return Violation.DOUBLE_THREE
                    }
                }

                Foul.DOUBLE_FOUR -> {
                    if (totalStoneCount > foul.size && forwardEmptyCount == 1 && backEmptyCount == 1) return Violation.DOUBLE_FOUR
                    if (totalStoneCount == foul.size && totalEmptyCount <= MAX_EMPTY_SIZE) continuousStones++
                    if (continuousStones == FOUL_CONDITION_SIZE) return Violation.DOUBLE_FOUR
                }
            }
        }
        return Violation.NONE
    }

    private fun findStraight(
        blackPositions: List<Stone>,
        whitePositions: List<Stone>,
        startPosition: Position,
        direction: Direction,
        foul: Foul,
    ): Pair<Int, Int> {
        var sameStoneCount = DEFAULT_SAME_STONE_COUNT
        var emptyCount = DEFAULT_EMPTY_COUNT
        val rowStep = direction.rowStep
        val colStep = direction.colStep
        var curPosition = startPosition.move(rowStep, colStep)

        while (curPosition.inRange(boardWidth, boardHeight) &&
            !whitePositions.isPlaced(curPosition) &&
            emptyCount <= MAX_EMPTY_SIZE &&
            sameStoneCount < foul.size
        ) {
            val hasBlackStone = blackPositions isPlaced curPosition
            val hasWhiteStone = whitePositions isPlaced curPosition
            val isEmpty = !hasBlackStone && !hasWhiteStone
            if (hasBlackStone) ++sameStoneCount
            if (isEmpty) ++emptyCount
            curPosition = curPosition.move(rowStep, colStep)
        }
        curPosition = curPosition.move(-rowStep, -colStep)
        while (curPosition.inRange(boardWidth, boardHeight) && startPosition != curPosition && !(blackPositions isPlaced curPosition)) {
            emptyCount -= 1
            curPosition = curPosition.move(-rowStep, -colStep)
        }
        return Pair(sameStoneCount, emptyCount)
    }

    private fun isBlockedByWhiteStoneInSix(
        whitePositions: List<Stone>,
        position: Position,
        direction: Direction,
    ): WhiteBlocked {
        val (oneDirMoveCount, oneDirFound) =
            checkWhite(
                whitePositions,
                position,
                direction,
                FORWARD_WEIGHT,
            )
        val (otherDirMoveCount, otherDirFound) =
            checkWhite(
                whitePositions,
                position,
                direction,
                BACK_WEIGHT,
            )
        val totalMoveCount = oneDirMoveCount + otherDirMoveCount
        return WhiteBlocked.from(
            totalMoveCount <= WhiteBlocked.INNER_DISTANCE &&
                oneDirFound &&
                otherDirFound,
        )
    }

    private fun checkWhite(
        whiteStones: List<Stone>,
        position: Position,
        direction: Direction,
        weight: Int,
    ): Pair<Int, Boolean> {
        val rowStep = direction.rowStep * weight
        val colStep = direction.colStep * weight
        var curPosition = position.move(rowStep, colStep)
        var moveCount = 0
        while (curPosition.inRange(boardWidth, boardHeight) && moveCount <= WhiteBlocked.INNER_DISTANCE) {
            moveCount++
            if (whiteStones isPlaced curPosition) return Pair(moveCount, true)
            curPosition = curPosition.move(rowStep, colStep)
        }
        return Pair(moveCount, false)
    }

    companion object {
        private const val FORWARD_WEIGHT = 1
        private const val BACK_WEIGHT = -1

        private const val DEFAULT_BOARD_WIDTH = 15
        private const val DEFAULT_BOARD_HEIGHT = 15

        private const val DEFAULT_SAME_STONE_COUNT = 1
        private const val DEFAULT_EMPTY_COUNT = 0
    }
}
