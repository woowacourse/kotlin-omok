package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.database.OmokDB


class MainActivity : AppCompatActivity() {

    private lateinit var boardViewsController: BoardViewsController
    private lateinit var omokDB: OmokDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardViews: List<ImageView> = findViewById<TableLayout>(R.id.board)
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>().toList()

        omokDB = OmokDB(this)
        boardViewsController = BoardViewsController(this, boardViews, omokDB)

        val restartButton = findViewById<Button>(R.id.button_restart)
        restartButton.setOnClickListener {
            boardViewsController.resetView()
            omokDB.deleteDB()
        }
    }
}
