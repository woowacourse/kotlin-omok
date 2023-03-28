package woowacourse.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class FakeEntryDao : EntryDao {
    private val entries: MutableMap<Long, Entry> = mutableMapOf()

    override fun save(entry: Entry): Entry {
        entries[entry.id] = entry
        return entry
    }

    override fun findAll(): List<Entry> {
        return entries.values.toList()
    }
}

class EntryServiceTest {
    private lateinit var entryDao: EntryDao

    @Before
    fun setUp() {
        entryDao = FakeEntryDao()
    }

    @Test
    fun 부제가_비어_있으면_없음으로_저장() {
        val entryService = EntryService(entryDao)
        entryService.save("test", "      ")
        val actual = entryDao.findAll().first { it.title == "test" }
        assertThat(actual.subtitle).isEqualTo("없음")
    }
}
