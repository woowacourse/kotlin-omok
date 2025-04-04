package woowacourse.omok.domain.model.game

import android.content.Context
import woowacourse.omok.OmokApplication
import woowacourse.omok.data.dao.OmokGameDao
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.place.PlaceResult
import woowacourse.omok.ui.mapper.toData
import woowacourse.omok.ui.mapper.toDomain

class OmokGameManager(
    context: Context,
    gameId: Int,
) {
    private val omokGameDao: OmokGameDao = (context.applicationContext as OmokApplication).omokGameDao
    val omokGame: OmokGame = OmokGame(omokGameDao.fetchGame(gameId)?.toDomain() ?: OmokGameEntity())
    val board: OmokBoard = omokGame.game.board

    fun placeStone(position: Position): PlaceResult {
        val result = omokGame.placeStone(position)

        return result
    }

    fun judgeMove(stone: PlayerStone): JudgeResult {
        val result = omokGame.judge(stone)

        when (result) {
            is JudgeResult.Finished -> omokGameDao.deleteGame(omokGame.game.id)
            is JudgeResult.NotFinished ->
                omokGameDao.saveGame(
                    omokGame.game.copy(lastTurn = omokGame.currentTurn).toData(),
                )
        }

        return result
    }

    fun restartGame() {
        omokGameDao.deleteGame(omokGame.game.id)
        omokGame.restart()
    }
}
