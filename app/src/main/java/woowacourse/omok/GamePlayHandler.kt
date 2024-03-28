package woowacourse.omok

// 어디서든 갖다 쓸 수 있도록. 콘솔에선 이걸 override해서 콘솔에 맞게, 안드로이드에서는 override해서 activity에 맞게.
interface GamePlayHandler {
    fun onDraw(board: Board)
}
