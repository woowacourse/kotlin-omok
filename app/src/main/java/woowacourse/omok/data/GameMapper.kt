package woowacourse.omok.data

import woowacourse.omok.domain.model.Game

fun GameEntity.toGame() = Game(id, name)
