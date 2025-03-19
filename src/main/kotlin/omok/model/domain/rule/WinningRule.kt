package omok.model.domain.rule

import omok.model.domain.omokboard.ColumnPosition
import omok.model.domain.omokboard.OmokBoard
import omok.model.domain.omokboard.PointState
import omok.model.domain.omokboard.Position
import omok.model.domain.omokboard.RowPosition
import omok.model.domain.player.PlayerStone
import omok.model.domain.player.StoneColor

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

        return PlaceResult.Success.Progress(playerStone)
    }

    private fun countStonesInDirection(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
        dx: Int,
        dy: Int,
    ): Int {
        val startPosition = playerStone.position
        val stoneColor = playerStone.color.toPointState()

        fun count(
            dx: Int,
            dy: Int,
        ): Int {
            var count = 0
            var currentPosition = startPosition

            while (true) {
                currentPosition =
                    Position(
                        RowPosition(currentPosition.row.value + dx),
                        ColumnPosition(currentPosition.column.value + dy),
                    )

                val point = omokBoard.find(currentPosition) ?: break

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
