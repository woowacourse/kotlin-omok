package woowacourse.omok.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.data.model.OmokGameDto

@RunWith(AndroidJUnit4::class)
class OmokGameDaoImplTest {
    private lateinit var dao: OmokGameDao
    private val gameId = 1

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val omokDbHelper = OmokDbHelper(context)
        dao = OmokGameDaoImpl(omokDbHelper, gameId)
        dao.deleteGame()
    }

    @Test
    fun saveGameAndFetch() {
        // given
        val input =
            OmokGameDto(
                lastTurn = "WHITE",
                board = mapOf(0 to 0 to "BLACK"),
            )

        // when
        dao.saveGame(input)
        val loaded = dao.fetchGame()

        // then
        assertThat(loaded).isNotNull
        assertThat(loaded!!.lastTurn).isEqualTo("WHITE")
        assertThat(loaded.board[0 to 0]).isEqualTo("BLACK")
    }

    @Test
    fun fetchGameWhenEmptyReturnsNull() {
        // when
        val game = dao.fetchGame()

        // then
        assertThat(game).isNull()
    }

    @Test
    fun deleteGameClearsSavedData() {
        // given
        dao.saveGame(OmokGameDto("WHITE", mapOf(1 to 1 to "WHITE")))

        // when
        dao.deleteGame()
        val afterDelete = dao.fetchGame()

        // then
        assertThat(afterDelete).isNull()
    }
}
