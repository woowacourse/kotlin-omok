package woowacourse.omok.domain.placeresult

import woowacourse.omok.domain.rule.GameResult

data class GameFinish(val gameResult: GameResult) : PlaceResult
