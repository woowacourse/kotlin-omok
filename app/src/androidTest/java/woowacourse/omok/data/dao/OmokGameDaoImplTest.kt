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
                id = gameId,
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

    @Test
    fun createGameGeneratesNewIdAndSaves() {
        // given
        val initial =
            OmokGameDto(
                id = 0,
                host = "HostA",
                lastTurn = "BLACK",
                board = OmokBoardDto(mapOf((2 to 2) to "BLACK")),
            )

        // when
        val newGameId = dao.createGame(initial)
        val loaded = dao.fetchGame(newGameId)

        // then
        assertThat(newGameId).isGreaterThan(0)
        assertThat(loaded).isNotNull
        assertThat(loaded!!.id).isEqualTo(newGameId)
        assertThat(loaded.host).isEqualTo("HostA")
        assertThat(loaded.lastTurn).isEqualTo("BLACK")
        assertThat(loaded.board.positions[2 to 2]).isEqualTo("BLACK")
    }

    @Test
    fun fetchAllGamesReturnsAllSavedGames() {
        // given
        val game1 =
            OmokGameDto(
                id = 1,
                host = "Host1",
                lastTurn = "WHITE",
                board = OmokBoardDto(mapOf((1 to 1) to "BLACK")),
            )
        val game2 =
            OmokGameDto(
                id = 2,
                host = "Host2",
                lastTurn = "BLACK",
                board = OmokBoardDto(mapOf((2 to 2) to "WHITE")),
            )
        dao.saveGame(game1)
        dao.saveGame(game2)

        // when
        val allGames = dao.fetchAllGames()

        // then
        assertThat(allGames.games).hasSizeGreaterThanOrEqualTo(2)

        val fetched1 = allGames.games.find { it.id == 1 }
        val fetched2 = allGames.games.find { it.id == 2 }

        assertThat(fetched1).isNotNull
        assertThat(fetched1!!.board.positions[1 to 1]).isEqualTo("BLACK")
        assertThat(fetched2).isNotNull
        assertThat(fetched2!!.board.positions[2 to 2]).isEqualTo("WHITE")
    }
}
