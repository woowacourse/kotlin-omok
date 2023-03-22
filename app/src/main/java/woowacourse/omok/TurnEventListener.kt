package woowacourse.omok

import android.content.Context
import android.widget.Toast
import domain.point.Point
import domain.stone.StoneColor
import listener.OmokTurnEventListener

class TurnEventListener(private val context: Context) : OmokTurnEventListener {
    lateinit var point: Point

    override fun onTakeTurn(stoneColor: StoneColor): Point = point

    override fun onNotPlaceable() {
        Toast.makeText(context, R.string.cant_place, Toast.LENGTH_LONG).show()
    }
}