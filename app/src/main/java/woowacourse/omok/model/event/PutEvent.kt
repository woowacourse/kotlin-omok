package omok.model.event

data class PutEvent(
    val onPutBlack: OnPlaceListener,
    val onPutWhite: OnPlaceListener,
)
