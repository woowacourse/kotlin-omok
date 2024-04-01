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
        val omokEntity = OmokEntity(0, 0)

        omokDao.insertStone(omokEntity)
        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.contains(omokEntity)).isTrue()
    }

    @Test
    fun findEmptyStones() {
        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.isEmpty()).isTrue()
    }

    @Test
    fun findAllStones() {
        val omokEntity1 = OmokEntity(0, 0)
        val omokEntity2 = OmokEntity(1, 1)

        omokDao.insertStone(omokEntity1)
        omokDao.insertStone(omokEntity2)

        val actual = omokDao.findAllStones()

        Assertions.assertThat(actual.containsAll(listOf(omokEntity1, omokEntity2))).isTrue()
    }

    @Test
    fun deleteAllStones() {
        val omokEntity1 = OmokEntity(0, 0)
        val omokEntity2 = OmokEntity(1, 1)

        omokDao.insertStone(omokEntity1)
        omokDao.insertStone(omokEntity2)

        omokDao.deleteAllStones()
        val actual = omokDao.findAllStones()
        Assertions.assertThat(actual.isEmpty()).isTrue()
    }
}
