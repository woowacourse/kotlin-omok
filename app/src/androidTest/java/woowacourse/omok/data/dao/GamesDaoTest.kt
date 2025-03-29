package woowacourse.omok.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import woowacourse.omok.data.OmokDatabaseHelper

@RunWith(AndroidJUnit4::class)
class GamesDaoTest {
    private lateinit var dbHelper: OmokDatabaseHelper
    private lateinit var gamesDao: GamesDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = OmokDatabaseHelper(context)
        gamesDao = GamesDao(dbHelper)
    }

    @After
    fun teardown() {
        clearAllGames()
        dbHelper.close()
    }

    private fun clearAllGames() {
        gamesDao.getGames().getOrDefault(emptyList()).forEach { game ->
            gamesDao.deleteGame(game.id)
        }
    }

    @Test
    fun `게임_생성_성공`() {
        val result = gamesDao.createGame("테스트 게임 1")
        assertAll(
            { assertTrue(result.isSuccess) },
            { assertThat(result.getOrNull()).isNotNull() },
        )
    }

    @Test
    fun `게임_삭제_성공`() {
        val gameId = gamesDao.createGame("테스트 게임 1").getOrThrow()
        val result = gamesDao.deleteGame(gameId)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `게임_상태_업데이트_성공`() {
        val gameId = gamesDao.createGame("테스트 게임 1").getOrThrow()
        val result = gamesDao.updateGameStatus(gameId)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `게임_목록_조회_성공`() {
        gamesDao.createGame("테스트 게임 1").getOrThrow()
        gamesDao.createGame("테스트 게임 2").getOrThrow()
        val result = gamesDao.getGames()

        assertAll(
            { assertTrue(result.isSuccess) },
            { assertThat(result.getOrThrow()).hasSize(2) },
        )
    }
}
