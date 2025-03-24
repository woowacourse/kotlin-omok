package omok.domain.service

import omok.domain.omokboard.PlayingBoard
import omok.domain.omokboard.Position
import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor
import omok.domain.rule.place.AlreadyExistStoneRule
import omok.domain.rule.place.ExternalRule
import omok.domain.rule.place.InvalidPositionRule
import omok.domain.rule.place.PlaceResult
import omok.domain.rule.place.PlaceRule
import omok.domain.rule.winning.DrawRule
import omok.domain.rule.winning.JudgeResult
import omok.domain.rule.winning.JudgeRule
import omok.domain.rule.winning.WinningRule

class OmokGame(
    private val playingBoard: PlayingBoard,
) {
    private val placeRules: List<PlaceRule> =
        listOf(
            InvalidPositionRule(),
            AlreadyExistStoneRule(),
            ExternalRule(),
        )

    private val judgeRules: List<JudgeRule> =
        listOf(
            WinningRule(),
            DrawRule(),
        )

    fun start(
        getNewPosition: (StoneColor, Position?) -> Position,
        onPlaceTried: (PlaceResult) -> Unit,
    ): JudgeResult.Finished {
        var stoneColor = StoneColor.BLACK
        var position: Position? = null

        while (true) {
            val playerStone = PlayerStone(stoneColor, getNewPosition(stoneColor, position))
            val placeResult = playingBoard.placeStone(placeRules, playerStone)
            onPlaceTried(placeResult)

            when (placeResult) {
                is PlaceResult.Success -> {
                    stoneColor = stoneColor.reversed()
                    position = playerStone.position
                    return when (val gameResult = playingBoard.judge(judgeRules, playerStone)) {
                        is JudgeResult.Finished.Win -> gameResult
                        is JudgeResult.Finished.Draw -> gameResult
                        JudgeResult.NotFinished -> continue
                    }
                }

                else -> continue
            }
        }
    }
}
