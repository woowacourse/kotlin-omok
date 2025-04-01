package woowacourse.omok.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.data.OmokDatabaseHelper

class GamesDaoTest {
    private lateinit var dbHelper: OmokDatabaseHelper
    private lateinit var gamesDao: GamesDao

    @BeforeEach
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = OmokDatabaseHelper(context, true)
        gamesDao = GamesDao(dbHelper)
    }

    @AfterEach
    fun teardown() {
        dbHelper.deleteDatabase()
        dbHelper.close()
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
