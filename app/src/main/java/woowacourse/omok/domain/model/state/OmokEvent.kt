package woowacourse.omok.domain.model.state

sealed interface OmokEvent {
    data object WIN : OmokEvent

    data object DRAW : OmokEvent

    data object TURN : OmokEvent
}
