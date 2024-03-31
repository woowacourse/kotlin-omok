package woowacourse.omok.model.data

interface OmokDao {
    fun save(omokEntity: OmokEntity): OmokEntity

    fun findAll(): List<OmokEntity>

    fun findLast(): OmokEntity?

    fun drop()
}
