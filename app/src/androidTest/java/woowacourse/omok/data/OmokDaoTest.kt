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
        assertOmokEntity(actual, OmokEntity(10, 10, STONE_BLACK))
    }

    @Test
    fun `여러_데이터를_저장한다`() {
        // given
        val omokEntities =
            listOf(
                OmokEntity(10, 10, STONE_BLACK),
                OmokEntity(5, 5, STONE_BLACK),
                OmokEntity(3, 7, STONE_WHITE),
            )

        // when
        val actual = omokDao.saveAll(omokEntities)

        // then
        assertThat(actual.size).isEqualTo(3)
        assertOmokEntity(actual[0], OmokEntity(10, 10, STONE_BLACK))
        assertOmokEntity(actual[1], OmokEntity(5, 5, STONE_BLACK))
        assertOmokEntity(actual[2], OmokEntity(3, 7, STONE_WHITE))
    }

    @Test
    fun `모든_데이터를_조회한다`() {
        // given
        val omokEntity = OmokEntity(5, 5, STONE_WHITE)
        omokDao.save(omokEntity)

        // when
        val actual = omokDao.findAll()

        // then
        assertOmokEntity(actual[0], OmokEntity(5, 5, STONE_WHITE))
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
        assertOmokEntity(actual!!, OmokEntity(10, 10, STONE_BLACK))
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

    private fun assertOmokEntity(
        actual: OmokEntity,
        expected: OmokEntity,
    ) {
        assertThat(actual.id).isGreaterThan(-1)
        assertThat(actual.row).isEqualTo(expected.row)
        assertThat(actual.col).isEqualTo(expected.col)
        assertThat(actual.stone).isEqualTo(expected.stone)
    }

    companion object {
        private const val STONE_BLACK = "black"
        private const val STONE_WHITE = "white"
    }
}
