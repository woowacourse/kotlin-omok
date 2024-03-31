package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import woowacourse.omok.omokdb.OmokDao
import woowacourse.omok.omokdb.OmokEntry

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private val dao = OmokDao(ApplicationProvider.getApplicationContext())

    @BeforeEach
    fun setUp() {
        dao.reset()
    }

    @Test
    fun `돌을_놓을_경우_돌의_위치와_색깔이_저장된다`() {
        // given
        val omokEntry = OmokEntry("A", 5, "흑")

        // when
        val actual = dao.insert(omokEntry)

        // then
        assertThat(actual.row).isEqualTo("A")
        assertThat(actual.column).isEqualTo(5)
        assertThat(actual.color).isEqualTo("흑")
    }

    @Test
    fun `돌을_놓은_모든_경우의_값을_가지고_온다`() {
        // given
        val omokEntry1 = OmokEntry("A", 1, "흑")
        val omokEntry2 = OmokEntry("A", 2, "백")

        // when
        dao.insert(omokEntry1)
        dao.insert(omokEntry2)
        val actual = dao.findAll()

        // then
        assertThat(actual[0].row).isEqualTo("A")
        assertThat(actual[0].column).isEqualTo(1)
        assertThat(actual[0].color).isEqualTo("흑")
        assertThat(actual[1].row).isEqualTo("A")
        assertThat(actual[1].column).isEqualTo(2)
        assertThat(actual[1].color).isEqualTo("백")
    }

    @Test
    fun `저장된_모든_데이터를_삭제한다`() {
        // given
        val omokEntry1 = OmokEntry("A", 1, "흑")
        val omokEntry2 = OmokEntry("A", 2, "백")
        dao.insert(omokEntry1)
        dao.insert(omokEntry2)

        // when
        dao.reset()
        val actual = dao.findAll()

        // then
        assertThat(actual).isEmpty()
    }
}
