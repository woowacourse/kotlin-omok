package woowacourse.omok.db

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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
        val stoneEntity = StoneEntity(0, 0)

        omokDao.insertStone(stoneEntity)
        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.contains(stoneEntity)).isTrue()
    }

    @Test
    fun findEmptyStones() {
        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.isEmpty()).isTrue()
    }

    @Test
    fun findAllStones() {
        val stoneEntity1 = StoneEntity(0, 0)
        val stoneEntity2 = StoneEntity(1, 1)

        omokDao.insertStone(stoneEntity1)
        omokDao.insertStone(stoneEntity2)

        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.containsAll(listOf(stoneEntity1, stoneEntity2))).isTrue()
    }

    @Test
    fun deleteAllStones() {
        val stoneEntity1 = StoneEntity(0, 0)
        val stoneEntity2 = StoneEntity(1, 1)

        omokDao.insertStone(stoneEntity1)
        omokDao.insertStone(stoneEntity2)

        omokDao.deleteAllStones()
        val actual = omokDao.findAllStones()
        Assertions.assertThat(actual.isEmpty()).isTrue()
    }
}
