package woowacourse.omok.domain.model.omokboard

import android.content.Context
import woowacourse.omok.data.dao.OmokGameDao
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.place.PlaceResult
import woowacourse.omok.ui.mapper.toData
import woowacourse.omok.ui.mapper.toUI

class OmokGameManager(
    context: Context,
) {
    private val omokGameDao: OmokGameDao = (context.applicationContext as woowacourse.omok.OmokApplication).omokGameDao
    var omokGame: OmokGame = loadOrCreateGame()

    private fun loadOrCreateGame(): OmokGame = omokGameDao.fetchGame(0)?.toUI() ?: OmokGame()

    fun placeStone(position: Position): PlaceResult {
        val result = omokGame.placeStone(position)

        if (result is PlaceResult.Success) omokGameDao.saveGame(omokGame.toData())

        return result
    }

    fun judgeMove(stone: PlayerStone): JudgeResult {
        val result = omokGame.judge(stone)

        when (result) {
            is JudgeResult.Finished -> omokGameDao.deleteGame(omokGame.game.id)
            is JudgeResult.NotFinished -> omokGameDao.saveGame(omokGame.toData())
        }

        return result
    }

    fun restartGame() {
        omokGameDao.deleteGame(omokGame.game.id)
        omokGame.restart()
    }
}
