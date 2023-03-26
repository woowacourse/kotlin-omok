package woowacourse.omok.database

import omok.model.stone.GoStone

interface Repository {
    fun getAll(): Map<GoStone, Int>
    fun insert(goStone: GoStone, index: Int)
    fun isEmpty(): Boolean
    fun clear()
    fun close()
}
