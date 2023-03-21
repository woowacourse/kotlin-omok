package domain.player

sealed class PlayerState{

    object Placing: PlayerState()

    object Waiting: PlayerState()
}
