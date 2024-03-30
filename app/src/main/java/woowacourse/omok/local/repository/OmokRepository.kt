package woowacourse.omok.local.repository

import woowacourse.omok.presentation.model.Omok

interface OmokRepository {
    fun insertOmok(omok: Omok): Result<Unit>

    fun selectOmok(): Result<List<Omok>>

    fun deleteOmok(omok: Omok): Result<Unit>

    fun deleteAllOmok(): Result<Unit>
}
