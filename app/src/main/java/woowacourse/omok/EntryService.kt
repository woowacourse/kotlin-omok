package woowacourse.omok

class EntryService(private val entryDao: EntryDao) {
    fun save(title: String, subtitle: String) {
        val realSubtitle = subtitle.ifBlank { "없음" }
        entryDao.save(Entry("test", realSubtitle))
    }

    fun findAllByTitle(title: String): List<Entry> {
        return entryDao.findAll().filter { it.title == title }
    }
}
