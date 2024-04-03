package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.game.database.OmokEntry
import woowacourse.omok.game.database.OmokEntryDao

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var dao: OmokEntryDao

    @BeforeEach
    fun setUp() {
        dao = OmokEntryDao(ApplicationProvider.getApplicationContext())
    }

    @AfterEach
    fun tearDown() {
        dao.drop()
    }

    @Test
    fun save() {
        val actual = dao.save(OmokEntry(1, "흑"))

        assertThat(actual.index).isEqualTo(1)
        assertThat(actual.color).isEqualTo("흑")
    }

    @Test
    fun findAll() {
        val actual = dao.findAll()
        assertThat(actual).isEmpty()
    }

    @Test
    fun saveAndFindAll() {
        val entry = OmokEntry(index = 1, color = "흑")
        dao.save(entry)

        val actual = dao.findAll()
        assertThat(actual[0].index).isEqualTo(1)
        assertThat(actual[0].color).isEqualTo("흑")
    }
}
