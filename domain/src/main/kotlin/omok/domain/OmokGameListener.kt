package omok.domain

interface OmokGameListener {
    fun onOmokStart()
    fun onBoardShow(omokBoard: OmokBoard)
    fun onError(message: String?)
}
