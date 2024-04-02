package woowacourse.omok.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
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
        omokDao.deleteAll()
    }

    @Test
    fun `데이터를_저장한다`() {
        // given
        val omokEntity = OmokEntity(10, 10, STONE_BLACK)

        // when
        val actual = omokDao.save(omokEntity)

        // then
        assertThat(actual.id).isGreaterThan(-1)
        assertThat(actual.row).isEqualTo(10)
        assertThat(actual.col).isEqualTo(10)
        assertThat(actual.stone).isEqualTo(STONE_BLACK)
    }

    @Test
    fun `모든_데이터를_조회한다`() {
        // given
        val omokEntity = OmokEntity(5, 5, STONE_WHITE)
        omokDao.save(omokEntity)

        // when
        val actual = omokDao.findAll()

        // then
        assertThat(actual[0].id).isGreaterThan(-1)
        assertThat(actual[0].row).isEqualTo(5)
        assertThat(actual[0].col).isEqualTo(5)
        assertThat(actual[0].stone).isEqualTo(STONE_WHITE)
    }

    @Test
    fun `가장_최근에_저장된_데이터를_조회한다`() {
        // given
        val omokEntity = OmokEntity(5, 5, STONE_WHITE)
        omokDao.save(omokEntity)
        omokDao.save(omokEntity)
        omokDao.save(OmokEntity(10, 10, STONE_BLACK))

        // when
        val actual = omokDao.findLastOrNull()

        // then
        assertThat(actual).isNotNull
        assertThat(actual?.id).isGreaterThan(-1)
        assertThat(actual?.row).isEqualTo(10)
        assertThat(actual?.col).isEqualTo(10)
        assertThat(actual?.stone).isEqualTo(STONE_BLACK)
    }

    @Test
    fun `모든_데이터를_삭제한다`() {
        // given
        val omokEntity = OmokEntity(7, 7, STONE_BLACK)
        omokDao.save(omokEntity)
        omokDao.save(omokEntity)

        // when
        omokDao.deleteAll()
        val actual = omokDao.findAll()

        // then
        assertThat(actual).isEmpty()
    }

    companion object {
        private const val STONE_BLACK = "black"
        private const val STONE_WHITE = "white"
    }
}
