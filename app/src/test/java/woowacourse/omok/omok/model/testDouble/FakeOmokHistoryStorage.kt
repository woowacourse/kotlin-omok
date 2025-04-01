package woowacourse.omok.omok.model.testDouble

import woowacourse.omok.data.History
import woowacourse.omok.data.OmokHistoryStorage

class FakeOmokHistoryStorage(
    private var histories: List<History> = emptyList(),
) : OmokHistoryStorage {
    override fun fetch(): List<History> = histories

    override fun add(history: History) {
        histories += history
    }

    override fun clear() {
        histories = emptyList()
    }

    override fun close() {
    }
}
