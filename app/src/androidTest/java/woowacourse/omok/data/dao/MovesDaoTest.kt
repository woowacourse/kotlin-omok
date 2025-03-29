package woowacourse.omok.data.dao

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point

@RunWith(AndroidJUnit4::class)
class MovesDaoTest {
    private lateinit var dbHelper: OmokDatabaseHelper
    private lateinit var movesDao: MovesDao
    private lateinit var gamesDao: GamesDao

    @BeforeEach
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = OmokDatabaseHelper(context)
        gamesDao = GamesDao(dbHelper)
        movesDao = MovesDao(dbHelper)
    }

    @AfterEach
    fun teardown() {
        dbHelper.close()
    }

    @Test
    fun `착수_저장_성공`() {
        val gameId = gamesDao.createGame("테스트 게임 1").getOrThrow()
        val move = Point(3, 3) to CellState.BLACK
        val result = movesDao.saveMove(gameId, move)
        assertTrue(result.isSuccess)
    }

    @Test
    fun `착수_조회_성공`() {
        val gameId = gamesDao.createGame("테스트 게임 1").getOrThrow()
        movesDao.saveMove(gameId, Point(1, 1) to CellState.BLACK)
        movesDao.saveMove(gameId, Point(2, 2) to CellState.WHITE)

        val result = movesDao.getMoves(gameId)
        val actual = result.getOrThrow().size
        assertAll(
            { assertTrue(result.isSuccess) },
            { assertThat(actual).isEqualTo(2) },
        )
    }
}
