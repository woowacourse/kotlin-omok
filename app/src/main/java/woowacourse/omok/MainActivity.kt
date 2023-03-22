package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import controller.GameController
import woowacourse.omok.view.AndroidGameView
import woowacourse.omok.view.AndroidRenderBoard
import woowacourse.omok.view.AndroidViewErrorHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageStones =
            findViewById<TableLayout>(R.id.board).children.filterIsInstance<TableRow>()
                .flatMap { it.children }.filterIsInstance<ImageView>().toList()

        GameController(
            AndroidGameView(applicationContext, AndroidRenderBoard(imageStones), imageStones),
            AndroidViewErrorHandler(applicationContext)
        ).process()
    }
}
