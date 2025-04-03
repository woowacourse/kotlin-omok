package woowacourse.omok.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.data.model.OmokBoardDto
import woowacourse.omok.data.model.OmokGameDto

@RunWith(AndroidJUnit4::class)
class OmokGameDaoImplTest {
    private lateinit var dao: OmokGameDao
    private val gameId = 1

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val omokDbHelper = OmokDbHelper(context)
        dao = OmokGameDaoImpl(omokDbHelper)
        dao.deleteGame(gameId)
    }

    @Test
    fun saveGameAndFetch() {
        // given
        val input =
            OmokGameDto(
                gameId = gameId,
                host = "",
                lastTurn = "WHITE",
                board = OmokBoardDto(mapOf(0 to 0 to "BLACK")),
            )

        // when
        dao.saveGame(input)
        val loaded = dao.fetchGame(gameId)

        // then
        assertThat(loaded).isNotNull
        assertThat(loaded!!.lastTurn).isEqualTo("WHITE")
        assertThat(loaded.board.positions[0 to 0]).isEqualTo("BLACK")
    }

    @Test
    fun fetchGameWhenEmptyReturnsNull() {
        // when
        val game = dao.fetchGame(gameId)

        // then
        assertThat(game).isNull()
    }

    @Test
    fun deleteGameClearsSavedData() {
        // given
        dao.saveGame(OmokGameDto(gameId, "", "WHITE", OmokBoardDto(mapOf(1 to 1 to "WHITE"))))

        // when
        dao.deleteGame(gameId)
        val afterDelete = dao.fetchGame(gameId)

        // then
        assertThat(afterDelete).isNull()
    }
}
