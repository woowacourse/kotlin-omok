package woowacourse.omok.data

class OmokHistoryRepository(
    private val storage: OmokHistoryStorage,
) {
    fun fetch(): List<History> = storage.fetch()

    fun add(history: History) {
        storage.add(history)
    }

    fun clear() {
        storage.clear()
    }

    fun close() {
        storage.close()
    }
}
