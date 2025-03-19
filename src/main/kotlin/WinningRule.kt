class WinningRule : OmokRule {
    override fun canPlace(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): PlaceResult {
        val directions =
            listOf(
                Pair(1, 0),
                Pair(0, 1),
                Pair(1, 1),
                Pair(1, -1),
            )

        for ((dx, dy) in directions) {
            if (countStonesInDirection(omokBoard, playerStone, dx, dy) >= 5) {
                return PlaceResult.Success.Finish(GameResult.WIN_BLACK)
            }
        }

        return PlaceResult.Success.Progress
    }

    private fun countStonesInDirection(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
        dx: Int,
        dy: Int,
    ): Int {
        val startX = playerStone.position.row.value
        val startY = playerStone.position.column.value
        val stoneColor = playerStone.color.toPointState()

        fun count(
            dx: Int,
            dy: Int,
        ): Int {
            var count = 0
            var x = startX
            var y = startY

            while (true) {
                x += dx
                y += dy
                val point =
                    omokBoard.value.find {
                        it.position.row.value == x && it.position.column.value == y
                    } ?: break

                if (point.state == stoneColor) count++ else break
            }
            return count
        }

        return 1 + count(dx, dy) + count(-dx, -dy)
    }

    private fun StoneColor.toPointState(): PointState =
        when (this) {
            StoneColor.BLACK -> PointState.OCCUPIED_BLACK
            StoneColor.WHITE -> PointState.OCCUPIED_WHITE
        }
}
