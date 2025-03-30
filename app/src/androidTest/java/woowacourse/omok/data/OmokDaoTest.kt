package woowacourse.omok.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import data.OmokDao
import domain.model.Point
import domain.model.state.BlackTurn
import domain.model.stone.BlackStones
import domain.model.stone.WhiteStones
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.fake.FakeDbHelper

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao
    private lateinit var fakeDbHelper: FakeDbHelper

    @BeforeEach
    fun setUp() {
        fakeDbHelper = FakeDbHelper(ApplicationProvider.getApplicationContext())
        omokDao = OmokDao(fakeDbHelper)
    }

    @Test
    fun testSaveGameState() {
        val state = BlackTurn(BlackStones(setOf(Point(1, 1))), WhiteStones(setOf(Point(2, 2))))
        omokDao.saveGameState(state)

        val savedState = omokDao.loadGameState()

        assertNotNull(savedState)
    }

    @Test
    fun testLoadGameState() {
        val state = BlackTurn(BlackStones(setOf(Point(1, 1))), WhiteStones(setOf(Point(2, 2))))
        omokDao.saveGameState(state)

        val loadedState = omokDao.loadGameState()

        assertTrue(loadedState is BlackTurn)
    }

    @Test
    fun testClearGameState() {
        val state = BlackTurn(BlackStones(setOf(Point(1, 1))), WhiteStones(setOf(Point(2, 2))))
        omokDao.saveGameState(state)

        omokDao.clearGameState()

        assertThat(omokDao.loadGameState()).isEqualTo(null)
    }
}
