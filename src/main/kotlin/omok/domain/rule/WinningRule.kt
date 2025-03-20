package omok.domain.rule

import omok.domain.omokboard.ColumnPosition
import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.PointState
import omok.domain.omokboard.Position
import omok.domain.omokboard.RowPosition
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import omok.domain.player.StoneColor.BLACK
import omok.domain.player.StoneColor.WHITE

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

        val gameResult =
            when (playerStone.color) {
                BLACK -> GameResult.WIN_BLACK
                WHITE -> GameResult.WIN_WHITE
            }

        for ((dx, dy) in directions) {
            if (countStonesInDirection(omokBoard, playerStone, dx, dy) >= 5) {
                return PlaceResult.Success.Finish(gameResult)
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
            BLACK -> PointState.OCCUPIED_BLACK
            WHITE -> PointState.OCCUPIED_WHITE
        }
}
