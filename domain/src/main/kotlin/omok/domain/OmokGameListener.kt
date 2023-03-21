package omok.domain

import omok.domain.state.StoneState

interface OmokGameListener {
    fun onOmokStart()
    fun onPointRequest(stoneState: StoneState, point: OmokPoint?): OmokPoint
    fun onBoardShow(omokBoard: OmokBoard)
    fun onError(message: String?)
}
