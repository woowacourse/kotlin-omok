package omok.model

interface ValidCoordinatesListener {
    fun onValidCoordinates(
        rowComma: String,
        columnComma: String,
    )

    fun onInvalidCoordinates()
}
