package woowacourse.omok.db

import woowacourse.omok.model.stone.StoneColor

interface TurnDao {
    fun saveTurn(color: StoneColor)

    fun getLastTurn(): StoneColor

    fun deleteTurn()
}
