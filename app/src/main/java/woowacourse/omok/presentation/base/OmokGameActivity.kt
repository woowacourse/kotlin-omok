package woowacourse.omok.presentation.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import omok.model.OMokGame
import woowacourse.omok.R
import woowacourse.omok.local.db.OmokDao
import woowacourse.omok.presentation.ui.Exception.DbException.DeleteAllOmokException
import woowacourse.omok.presentation.ui.Exception.DbException.DeleteOmokException
import woowacourse.omok.presentation.ui.Exception.DbException.InsertOmokException
import woowacourse.omok.presentation.ui.Exception.DbException.SelectOmokException

abstract class OmokGameActivity(private val layoutResID: Int) : AppCompatActivity() {
    abstract var dao: OmokDao
    abstract var oMokGame: OMokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID)
        dao = OmokDao(this)
        initStartView()
    }

    abstract fun initStartView()

    abstract fun initOmok()

    abstract fun resetOmokClickListener()

    abstract fun setupBoardClickListener()

    fun showSnackbar(
        view: View,
        message: String,
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackbar(
        view: View,
        e: Throwable,
    ) {
        val message =
            when (e) {
                is InsertOmokException -> getString(R.string.insert_omok_exceptions_message)

                is SelectOmokException -> getString(R.string.select_omok_exceptions_message)

                is DeleteOmokException -> getString(R.string.delete_omok_exceptions_message)

                is DeleteAllOmokException -> getString(R.string.delete_all_omok_exceptions_message)

                else -> getString(R.string.base_exceptions_message)
            }

        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}
