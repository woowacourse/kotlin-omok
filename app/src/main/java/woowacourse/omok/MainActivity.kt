package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import controller.GameController
import woowacourse.omok.db.table.GamePlaceTable
import woowacourse.omok.db.table.GameTable
import woowacourse.omok.db.OmokDBHelper
import woowacourse.omok.db.table.PlaceTable
import woowacourse.omok.db.table.UserGameTable
import woowacourse.omok.db.table.UserTable
import woowacourse.omok.view.AndroidGameView
import woowacourse.omok.view.AndroidRenderBoard
import woowacourse.omok.view.AndroidViewErrorHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tables = listOf(
            UserTable(), UserGameTable(), GameTable(), GamePlaceTable(), PlaceTable()
        )

        val omokDBHelper = OmokDBHelper(this, tables)
        omokDBHelper.writableDatabase.execSQL("INSERT INTO USER VALUES(0, \"dy\")")

        val imageStones =
            findViewById<TableLayout>(R.id.board).children.filterIsInstance<TableRow>()
                .flatMap { it.children }.filterIsInstance<ImageView>().toList()

        GameController(
            AndroidGameView(
                this,
                AndroidRenderBoard(imageStones),
                imageStones
            ),
            AndroidViewErrorHandler(this)
        ).process()
    }
}
