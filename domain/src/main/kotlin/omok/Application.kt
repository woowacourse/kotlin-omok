package omok

fun main() {
    runCatching {
        Controller(GameView(), OmokGame()).gameStart()
    }.onFailure { exception ->
        GameView().printErrorMessage(exception)
    }
}
