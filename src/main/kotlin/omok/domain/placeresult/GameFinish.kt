package omok.domain.placeresult

import omok.domain.rule.GameResult

data class GameFinish(val gameResult: GameResult) : PlaceResult
