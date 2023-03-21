package omok.domain

interface OmokGameListener {
    fun onOmokStart()
    fun onBoardShow(omokBoard: OmokBoard, omokPoint: OmokPoint?)
    fun onError(message: String?)
}
