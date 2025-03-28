package woowacourse.omok.domain.model.rule.judge

import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor.BLACK
import woowacourse.omok.domain.model.player.StoneColor.WHITE

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
                        currentPosition.row + dx,
                        currentPosition.column + dy,
                    )

                val point = omokBoard.find(currentPosition) ?: break

                if (point == stoneColor) count++ else break
            }
            return count
        }

        return 1 + count(dx, dy) + count(-dx, -dy)
    }
}
