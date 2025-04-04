package woowacourse.omok.domain.model.game

import android.content.Context
import woowacourse.omok.OmokApplication
import woowacourse.omok.data.dao.OmokGameDao
import woowacourse.omok.domain.model.player.PlayerName
import woowacourse.omok.ui.mapper.toData
import woowacourse.omok.ui.mapper.toDomain

class LobbyManager(
    context: Context,
) {
    private val omokGameDao: OmokGameDao = (context.applicationContext as OmokApplication).omokGameDao

    fun getAllGames(): List<OmokGameEntity> = omokGameDao.fetchAllGames().toDomain()

    fun createNewGame(): Int {
        val newGame = OmokGameEntity(host = PlayerName.create())
        return omokGameDao.createGame(newGame.toData())
    }
}
