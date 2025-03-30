package woowacourse.omok.data

interface StoneDataSource {
    fun fetchAllStones(): List<StoneDao>

    fun insert(stoneDao: StoneDao)

    fun deleteAll()
}
