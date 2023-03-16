package domain

object Rule {

    fun canPut(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean =
        !(
            is33(blackStones, whiteStones, nextStone) || is44(blackStones, whiteStones, nextStone) || isLongMok(
                blackStones,
                nextStone,
            )
            )

    private fun is33(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        val nextStones = blackStones + nextStone
        var count33 = 0
        if (isRowOpen4(blackStones, whiteStones, nextStone)) count33++

        return count33 >= 2
    }

    // 가로 판단
    private fun isRowOpen4(blackStones: Set<Stone>, whiteStones: Set<Stone>, justPlacedStone: Stone): Boolean {
        var (leftX, leftY) = justPlacedStone.xCoordinate.x to justPlacedStone.yCoordinate.y
        while (leftX > XCoordinate.X_MIN_RANGE && Stone(
                XCoordinate.of(leftX),
                YCoordinate.of(leftY),
            ) !in whiteStones && Stone(leftX, leftY) in blackStones
        ) {
            leftX -= 1
        }
        var (rightX, rightY) = justPlacedStone.xCoordinate.x to justPlacedStone.yCoordinate.y
        while (rightX < XCoordinate.X_MAX_RANGE && Stone(
                XCoordinate.of(leftX),
                YCoordinate.of(leftY),
            ) !in whiteStones && Stone(leftX, leftY) in blackStones
        ) {
            rightX += 1
        }
        val leftStone = Stone(leftX, leftY)
        val rightStone = Stone(rightX, rightY)
        if (leftStone.isInRange() && leftStone !in blackStones && leftStone !in whiteStones && rightStone.isInRange() && rightStone !in blackStones && rightStone !in whiteStones) {
            if (rightX - leftX == 5) return true
        }
        return false
    }

    private fun is44(blackStones: Set<Stone>, whiteStones: Set<Stone>, nextStone: Stone): Boolean {
        return false
    }

    private fun isLongMok(stones: Set<Stone>, nextStone: Stone): Boolean {
        return false
    }

    private fun Stone.isInRange(): Boolean =
        this.xCoordinate.x in (XCoordinate.X_MIN_RANGE..XCoordinate.X_MAX_RANGE) &&
            this.yCoordinate.y in (YCoordinate.Y_MIN_RANGE..YCoordinate.Y_MAX_RANGE)
}
