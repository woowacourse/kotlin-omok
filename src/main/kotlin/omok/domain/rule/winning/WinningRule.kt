package omok.domain.rule.winning

import omok.domain.omokboard.ColumnPosition
import omok.domain.omokboard.IntersectionState
import omok.domain.omokboard.OmokBoard
import omok.domain.omokboard.Position
import omok.domain.omokboard.RowPosition
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import omok.domain.player.StoneColor.BLACK
import omok.domain.player.StoneColor.WHITE

class WinningRule : JudgeRule {
    override fun perform(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
    ): JudgeResult {
        val directions =
            listOf(
                Pair(1, 0),
                Pair(0, 1),
                Pair(1, 1),
                Pair(1, -1),
            )

        val judgeResult =
            when (playerStone.color) {
                BLACK -> JudgeResult.Finished.Win(playerStone.color)
                WHITE -> JudgeResult.Finished.Win(playerStone.color)
            }

        for ((dx, dy) in directions) {
            if (countStonesInDirection(omokBoard, playerStone, dx, dy) >= 5) {
                return judgeResult
            }
        }

        return JudgeResult.NotFinished
    }

    private fun countStonesInDirection(
        omokBoard: OmokBoard,
        playerStone: PlayerStone,
        dx: Int,
        dy: Int,
    ): Int {
        val startPosition = playerStone.position
        val stoneColor = playerStone.color.toIntersectionState()

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

    private fun StoneColor.toIntersectionState(): IntersectionState =
        when (this) {
            BLACK -> IntersectionState.OCCUPIED_BLACK
            WHITE -> IntersectionState.OCCUPIED_WHITE
        }
}
