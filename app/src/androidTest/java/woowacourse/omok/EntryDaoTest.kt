package woowacourse.omok

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EntryDaoTest {
    private lateinit var entryDao: SQLiteEntryDao

    @Before
    fun setUp() {
        entryDao = SQLiteEntryDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        entryDao.drop()
    }

    @Test
    fun save() {
        val entry = Entry("test1", "test2")
        val actual = entryDao.save(entry)
        assertThat(actual.id).isNotZero
    }

    @Test
    fun findAll() {
        val entries = entryDao.findAll()
        assertThat(entries).hasSize(0)
    }
}
