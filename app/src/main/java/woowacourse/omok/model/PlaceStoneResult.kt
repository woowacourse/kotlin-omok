package omok.model

sealed class PlaceStoneResult

class Success(val board: Board) : PlaceStoneResult()

class StoneOutOfBoard : PlaceStoneResult()

class StoneAlreadyExists : PlaceStoneResult()
