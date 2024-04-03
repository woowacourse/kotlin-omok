package woowacourse.omok.db

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OmokEntryDaoTest {
    private lateinit var omokEntryDao: OmokEntryDao

    @Before
    fun setUp() {
        omokEntryDao = OmokEntryDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        omokEntryDao.delete()
    }

    @Test
    fun save() {
        val actual =
            omokEntryDao.save(OmokEntry(x = 6, y = 1, color = "흑"))

        assertThat(actual.id > 0).isTrue()
        assertThat(actual.x).isEqualTo(6)
        assertThat(actual.y).isEqualTo(1)
        assertThat(actual.color).isEqualTo("흑")
    }

    @Test
    fun findAllDatabase() {
        val actual = omokEntryDao.findAllDatabase()
        assertThat(actual).isEmpty()
    }

    @Test
    fun saveAndFindAllDatabase() {
        val entry = OmokEntry(x = 1, y = 6, color = "흑")
        omokEntryDao.save(entry)

        val actual = omokEntryDao.findAllDatabase()
        assertThat(actual[0].x).isEqualTo(1)
        assertThat(actual[0].y).isEqualTo(6)
        assertThat(actual[0].color).isEqualTo("흑")
    }
}
