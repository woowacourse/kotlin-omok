package woowacourse.omok.data.datasource

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.db.OmokEntity
import woowacourse.omok.data.fake.FakeOmokSQLiteHelper
import woowacourse.omok.fixture.context

@RunWith(AndroidJUnit4::class)
class OmokDataSourceTest {
    private lateinit var omokDataSource: OmokDataSource

    @BeforeEach
    fun setUp() {
        omokDataSource = OmokDataSource(FakeOmokSQLiteHelper(context))
    }

    @AfterEach
    fun dropDown() {
        omokDataSource.drop()
    }

    @DisplayName("데이터베이스에 현재 저장된 오목돌들을 가져온다")
    @Test
    fun test1() {
        // given
        val entities =
            listOf(
                OmokEntity(1, 1, "BLACK"),
                OmokEntity(2, 2, "WHITE"),
                OmokEntity(3, 3, "BLACK"),
            )
        entities.forEach { omokDataSource.save(it) }

        // when
        val actual = omokDataSource.readAll()

        // then
        assertThat(actual).containsExactlyElementsOf(entities)
    }

    @DisplayName("데이터 베이스에 현재 저장된 돌들을 제거한다")
    @Test
    fun test2() {
        // given
        omokDataSource.save(OmokEntity(1, 1, "BLACK"))

        // when
        omokDataSource.drop()
        val actual = omokDataSource.readAll()

        // then
        assertThat(actual).isEmpty()
    }

    @DisplayName("같은 위치에 중복 저장하지 않는다")
    @Test
    fun `test3`() {
        // given
        val entity1 = OmokEntity(1, 1, "BLACK")
        val entity2 = OmokEntity(1, 1, "WHITE")
        omokDataSource.save(entity1)
        omokDataSource.save(entity2)

        // when
        val actual = omokDataSource.readAll()

        // then
        assertThat(actual).containsExactly(entity1)
    }
}
