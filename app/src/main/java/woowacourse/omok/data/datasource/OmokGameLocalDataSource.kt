package woowacourse.omok.data.datasource

import woowacourse.omok.data.model.OmokGameInfoDto

interface OmokGameLocalDataSource {
    fun save(omokGameInfoDto: OmokGameInfoDto)

    fun load(): OmokGameInfoDto?

    fun delete()
}
