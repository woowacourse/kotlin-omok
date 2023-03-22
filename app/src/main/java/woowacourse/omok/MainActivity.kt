package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.*

class MainActivity : AppCompatActivity() {
    private val gameBoard = Board(Stones())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var currentColor = Color.BLACK
        val viewboard = findViewById<TableLayout>(R.id.board)
        viewboard
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    if (gameBoard.processTurn(
                            Stone(
                                currentColor,
                                Coordinate.from(index % 15, index / 15)!!
                            )
                        )
                    ) {
                        when (currentColor) {
                            Color.BLACK -> view.setImageDrawable(getDrawable(R.drawable.black_stone))
                            Color.WHITE -> view.setImageDrawable(getDrawable(R.drawable.white_stone))
                        }
                        if (gameBoard.isWinPlace()) {
                            Toast.makeText(this, "${currentColor} 승", Toast.LENGTH_SHORT).show()
                        }
                        currentColor = currentColor.turnColor()
                    }
                }
            }
    }

}
