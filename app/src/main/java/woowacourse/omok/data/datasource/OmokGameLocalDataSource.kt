package woowacourse.omok.data.datasource

import woowacourse.omok.data.model.OmokGameDto

interface OmokGameLocalDataSource {
    fun save(omokGameDto: OmokGameDto)

    fun load(): OmokGameDto?

    fun delete()
}
