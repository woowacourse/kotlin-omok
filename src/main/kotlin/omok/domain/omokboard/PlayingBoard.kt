package omok.domain.omokboard

import omok.domain.player.PlayerStone
import omok.domain.rule.OmokRule
import omok.domain.rule.place.PlaceResult
import omok.domain.rule.winning.JudgeResult

class PlayingBoard(
    val board: OmokBoard = OmokBoard.create(),
) {
    fun placeStone(
        rules: List<OmokRule>,
        playerStone: PlayerStone,
    ): PlaceResult {
        var result: PlaceResult = PlaceResult.Success(playerStone)

        rules.forEach { rule ->
            result = rule.perform(board, playerStone) as PlaceResult
            if (result is PlaceResult.Failure) return result
        }

        if (result is PlaceResult.Success) {
            board
                .find(playerStone.position)
                ?.updateState(playerStone.color)
        }

        return result
    }

    fun judge(
        rules: List<OmokRule>,
        playerStone: PlayerStone,
    ): JudgeResult {
        var result: JudgeResult = JudgeResult.NotFinished

        rules.forEach { rule ->
            result = rule.perform(board, playerStone) as JudgeResult
            if (result is JudgeResult.Finished) return result
        }

        return result
    }
}
