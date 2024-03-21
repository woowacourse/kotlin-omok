package omok.model.event

import omok.model.Position

data class PutEvent(
    val onPutBlack: () -> Position,
    val onPutWhite: () -> Position,
) {

    fun reverse(event: () -> Position): () -> Position {
        if (event == onPutBlack) return onPutWhite
        return onPutBlack
    }
}
