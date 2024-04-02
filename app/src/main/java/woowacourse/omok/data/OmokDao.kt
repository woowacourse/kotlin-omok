package woowacourse.omok.data

interface OmokDao {
    fun save(omokEntity: OmokEntity): OmokEntity

    fun saveAll(omokEntities: List<OmokEntity>) {
        omokEntities.forEach { save(it) }
    }

    fun findAll(): List<OmokEntity>

    fun findLast(): OmokEntity?

    fun drop()
}
