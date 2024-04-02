package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
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

    @After
    fun tearDown() {
        dao.reset()
    }

    @Test
    fun `id값에_해당하는_데이터를_가져온다`() {
        // given
        val omokEntry1 = OmokEntry("A", 5, "흑", 1)
        val omokEntry2 = OmokEntry("A", 6, "흑", 2)
        dao.insert(omokEntry1)
        dao.insert(omokEntry2)

        // when
        val actual = dao.findEntryById(2)

        // then
        assertThat(actual).isEqualTo(omokEntry2)
    }

    @Test
    fun `돌을_놓을_경우_돌의_위치와_색깔이_저장된다`() {
        // given
        val omokEntry = OmokEntry("A", 5, "흑", 1)

        // when
        val actual = dao.insert(omokEntry)
        val expected = dao.findEntryById(1)
        println(actual)
        println(expected)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌을_놓은_모든_경우의_값을_가지고_온다`() {
        // given
        val omokEntry1 = OmokEntry("A", 1, "흑", 1)
        val omokEntry2 = OmokEntry("A", 2, "백", 2)
        dao.insert(omokEntry1)
        dao.insert(omokEntry2)

        // when
        val actual = dao.findAll()
        val expected1 = dao.findEntryById(1)
        val expected2 = dao.findEntryById(2)

        // then
        assertThat(actual[0]).isEqualTo(expected1)
        assertThat(actual[1]).isEqualTo(expected2)
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
