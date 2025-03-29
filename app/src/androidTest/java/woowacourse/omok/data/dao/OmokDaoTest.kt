package woowacourse.omok.data.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.db.omok.OmokEntity
import woowacourse.omok.data.fake.FakeOmokSQLiteHelper
import woowacourse.omok.fixture.testContext

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao

    @BeforeEach
    fun setUp() {
        omokDao = OmokDao(FakeOmokSQLiteHelper(testContext))
    }

    @AfterEach
    fun dropDown() {
        omokDao.drop()
    }

    @DisplayName("데이터베이스에 현재 저장된 오목돌들을 가져온다")
    @Test
    fun test1() {
        // given
        val entities =
            arrayOf(
                OmokEntity(1, 1, "BLACK"),
                OmokEntity(2, 2, "WHITE"),
                OmokEntity(3, 3, "BLACK"),
            )
        entities.forEach { omokDao.save(it) }

        // when
        val actual = omokDao.readAll()

        // then
        assertThat(actual).containsExactly(*entities)
    }

    @DisplayName("데이터 베이스에 현재 저장된 돌들을 제거한다")
    @Test
    fun test2() {
        // given
        omokDao.save(OmokEntity(1, 1, "BLACK"))

        // when
        omokDao.drop()
        val actual = omokDao.readAll()

        // then
        assertThat(actual).isEmpty()
    }

    @DisplayName("같은 위치에 중복 저장하지 않는다")
    @Test
    fun `test3`() {
        // given
        val entities = arrayOf(OmokEntity(1, 1, "BLACK"), OmokEntity(1, 1, "WHITE"))

        entities.forEach { omokDao.save(it) }

        // when
        val actual = omokDao.readAll()

        // then
        assertThat(actual).isEqualTo(listOf((OmokEntity(1, 1, "BLACK"))))
    }
}
