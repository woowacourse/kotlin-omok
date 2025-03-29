package woowacourse.omok.domain.rule

import woowacourse.omok.domain.omokboard.ColumnPosition
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.OmokBoardGridCell
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.omokboard.RowPosition
import woowacourse.omok.domain.placeresult.GameFinish
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.player.StoneColor.BLACK
import woowacourse.omok.domain.player.StoneColor.WHITE

class WinningRule : OmokGameFinishRule {
    override fun place(
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
                return GameFinish(gameResult)
            }
        }

        return GameOnGoing
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

                if (point is OmokBoardGridCell.OCCUPIED && point.color == stoneColor.color) count++ else break
            }
            return count
        }

        return 1 + count(dx, dy) + count(-dx, -dy)
    }

    private fun StoneColor.toPointState(): OmokBoardGridCell.OCCUPIED = OmokBoardGridCell.OCCUPIED(this)
}
