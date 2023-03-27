package woowacourse.omok.presentation.presenter.omok

interface OmokContract {
    interface View {

    }

    interface Presenter<T: View> {
        fun onAttach(view: T)
        fun onDetach()
    }
}
