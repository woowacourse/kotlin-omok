package woowacourse.omok.model.board

sealed interface BoardState

class Duplicated(val message: String) : BoardState

class OutOfRange(val message: String) : BoardState

class Full(val message: String) : BoardState

class Success(val board: Board) : BoardState
