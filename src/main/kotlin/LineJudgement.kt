class LineJudgement(val player: Player, val position: Position) {
    fun checkHorizontal(): Boolean {
        var count = 0
        var prev = true
        var present: Boolean

        HorizontalAxis.values().forEach { horizontal ->
            present = player.stones.any { stone -> stone.findPosition(Position(horizontal, position.verticalAxis)) }
            if ((count == 0 && present) || (prev && present))
                count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5)
                return true
        }
        return false
    }

    fun checkVertical(): Boolean {
        var count = 0
        var prev = true
        var present: Boolean

        (1..15).forEach { vertical ->
            present = player.stones.any { stone -> stone.findPosition(Position(position.horizontalAxis, vertical)) }
            if ((count == 0 && present) || (prev && present))
                count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5)
                return true
        }
        return false
    }

    fun checkMajorDiagonal(): Boolean {
        if (position.horizontalAxis.value <= position.verticalAxis) {
            return checkUpperMajorDiagonal(position.horizontalAxis.value, position.verticalAxis)
        }
        return checkLowerMajorDiagonal(position.horizontalAxis.value, position.verticalAxis)
    }

    private fun checkUpperMajorDiagonal(horizontalAxis: Int, verticalAxis: Int): Boolean {
        var count = 0
        var prev = true
        var present: Boolean

        var vertical = verticalAxis - horizontalAxis + 1

        (1..15 + horizontalAxis - verticalAxis).forEach { horizontal ->
            present = player.stones.any { stone -> stone.findPosition(Position(HorizontalAxis.getHorizontalAxis(horizontal), vertical)) }
            vertical++
            if ((count == 0 && present) || (prev && present)) count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5) return true
        }
        return false
    }

    private fun checkLowerMajorDiagonal(horizontalAxis: Int, verticalAxis: Int): Boolean {
        var count = 0
        var prev = true
        var present: Boolean

        var horizontal = horizontalAxis - verticalAxis + 1

        (1..15 - horizontalAxis + verticalAxis).forEach { vertical ->
            present = player.stones.any { stone -> stone.findPosition(Position(HorizontalAxis.getHorizontalAxis(horizontal), vertical)) }
            horizontal++
            if ((count == 0 && present) || (prev && present)) count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5) return true
        }
        return false
    }

    fun checkSubDiagonal(): Boolean {
        if (position.horizontalAxis.value <= position.verticalAxis) {
            return checkUpperSubDiagonal(position.horizontalAxis.value, position.verticalAxis)
        }
        return checkLowerSubDiagonal(position.horizontalAxis.value, position.verticalAxis)
    }

    private fun checkUpperSubDiagonal(horizontalAxis: Int, verticalAxis: Int): Boolean {
        var count = 0
        var prev = true
        var present: Boolean

        var vertical = horizontalAxis + verticalAxis - 15

        (15..horizontalAxis + verticalAxis - 15).forEach { horizontal ->
            present = player.stones.any { stone -> stone.findPosition(Position(HorizontalAxis.getHorizontalAxis(horizontal), vertical)) }
            vertical++
            if ((count == 0 && present) || (prev && present)) count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5) return true
        }
        return false
    }

    private fun checkLowerSubDiagonal(horizontalAxis: Int, verticalAxis: Int): Boolean {
        var count = 0
        var prev = true
        var present: Boolean

        var vertical = horizontalAxis + verticalAxis - 1

        (1..horizontalAxis + verticalAxis - 1).forEach { horizontal ->
            present = player.stones.any { stone -> stone.findPosition(Position(HorizontalAxis.getHorizontalAxis(horizontal), vertical)) }
            vertical--
            if ((count == 0 && present) || (prev && present)) count++
            if (!present) {
                prev = false
                count = 0
            }
            prev = present
            if (count == 5) return true
        }
        return false
    }
}
