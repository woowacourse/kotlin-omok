package omok.model.game

sealed interface OmokGameResult

data object Placed : OmokGameResult

data object InvalidGameRule : OmokGameResult
