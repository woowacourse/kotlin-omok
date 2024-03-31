package woowacourse.omok.db

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao

    @Before
    fun setUp() {
        omokDao = OmokDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        omokDao.deleteAllStones()
    }

    @Test
    fun insertStone() {
        val stone = Stone(StoneType.BLACK, Point(0, 0))

        omokDao.insertStone(stone)
        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.contains(stone)).isTrue()
    }

    @Test
    fun findEmptyStones() {
        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.isEmpty()).isTrue()
    }

    @Test
    fun findAllStones() {
        val stone1 = Stone(StoneType.BLACK, Point(0, 0))
        val stone2 = Stone(StoneType.WHITE, Point(1, 1))

        omokDao.insertStone(stone1)
        omokDao.insertStone(stone2)

        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.containsAll(listOf(stone1, stone2))).isTrue()
    }

    @Test
    fun deleteAllStones() {
        val stone1 = Stone(StoneType.BLACK, Point(0, 0))
        val stone2 = Stone(StoneType.WHITE, Point(1, 1))

        omokDao.insertStone(stone1)
        omokDao.insertStone(stone2)

        omokDao.deleteAllStones()
        val actual = omokDao.findAllStones()
        Assertions.assertThat(actual.isEmpty()).isTrue()
    }
}
