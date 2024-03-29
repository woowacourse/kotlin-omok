package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.view.output.AndroidOutputView
import woowacourse.omok.view.output.OutputView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val outputView: OutputView = AndroidOutputView(board)

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    view.setImageResource(R.drawable.black_stone)
                }
            }

        printStartGuide(outputView)
    }

    private fun printStartGuide(outputView: OutputView) {
        outputView.printStartGuide()
    }
}
