package woowacourse.omok.domain.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.datasource.OmokGameLocalDataSourceImpl
import woowacourse.omok.domain.model.game.OmokGameEntity
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor

@RunWith(AndroidJUnit4::class)
class OmokGameRepositoryImplTest {
    private lateinit var omokGameRepositoryImpl: OmokGameRepositoryImpl

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val omokGameLocalDataSourceImpl = OmokGameLocalDataSourceImpl(context)
        omokGameRepositoryImpl = OmokGameRepositoryImpl(omokGameLocalDataSourceImpl)

        runBlocking {
            omokGameRepositoryImpl.deleteGame()
        }
    }

    @Test
    fun saveGame() {
        runBlocking {
            // given
            val board = OmokBoard(mutableMapOf(Position(0, 0) to PointState.OCCUPIED_BLACK))
            val game =
                OmokGameEntity(
                    lastTurn = StoneColor.WHITE,
                    board = board,
                )

            // when
            omokGameRepositoryImpl.saveGame(game)
            val saved = omokGameRepositoryImpl.fetchGame()

            // then
            assertThat(saved.board.find(Position(0, 0))).isEqualTo(PointState.OCCUPIED_BLACK)
            assertThat(saved.lastTurn).isEqualTo(StoneColor.WHITE)
        }
    }

    @Test
    fun fetchGameWhenEmpty() {
        runBlocking {
            // given && when
            val game = omokGameRepositoryImpl.fetchGame()

            // then
            assertThat(game.board.snapshot).anySatisfy { _, point -> assertThat(point).isEqualTo(PointState.EMPTY) }
            assertThat(game.lastTurn).isEqualTo(StoneColor.BLACK)
        }
    }

    @Test
    fun deleteGame() {
        runBlocking {
            // given
            val board = OmokBoard(mutableMapOf(Position(1, 1) to PointState.OCCUPIED_WHITE))
            val game =
                OmokGameEntity(
                    lastTurn = StoneColor.WHITE,
                    board = board,
                )
            omokGameRepositoryImpl.saveGame(game)

            // when
            omokGameRepositoryImpl.deleteGame()
            val afterDelete = omokGameRepositoryImpl.fetchGame()

            // then
            assertThat(afterDelete.board.snapshot).anySatisfy { _, point -> assertThat(point).isEqualTo(PointState.EMPTY) }
        }
    }
}
