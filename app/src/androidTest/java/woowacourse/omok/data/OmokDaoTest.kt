package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.assertions.assertSoftly
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.StoneType

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var databaseHelper: FakeOmokDatabaseHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var omokDao: OmokDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        databaseHelper = FakeOmokDatabaseHelper(context)
        database = databaseHelper.writableDatabase
        omokDao = OmokDaoImpl(database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    @DisplayName("돌의 위치와 타입을 저장하고 가져온다")
    fun saveStoneTest() {
        // Given
        val position = Position.of(3, 3, 15)
        val stoneType = StoneType.BLACK

        // When
        omokDao.saveStone(position, stoneType)
        val stones = omokDao.loadStones()

        // Then
        assertSoftly(stones) {
            assertThat(size).isEqualTo(1)
            assertThat(first().first).isEqualTo(position)
            assertThat(first().second).isEqualTo(stoneType)
        }
    }

    @Test
    @DisplayName("게임의 종료 여부를 저장하고 가져온다")
    fun saveFinishedTest() {
        // Given
        omokDao.saveGameFinished(true)

        // When
        val isFinished = omokDao.isGameFinished()

        // Then
        assertThat(isFinished).isTrue()
    }

    @Test
    @DisplayName("게임 데이터를 모두 지운다")
    fun clearTest() {
        // Given
        omokDao.saveStone(Position.of(1, 1, 15), StoneType.BLACK)
        omokDao.saveGameFinished(true)

        // When
        omokDao.clearGameData()

        // Then
        assertSoftly(omokDao) {
            assertThat(loadStones()).isEmpty()
            assertThat(isGameFinished()).isFalse()
        }
    }
}
