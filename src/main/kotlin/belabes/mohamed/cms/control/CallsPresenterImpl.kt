package belabes.mohamed.cms.control

import belabes.mohamed.cms.CallsPresenter
import belabes.mohamed.cms.Model

class CallsPresenterImpl(private val model: Model, private val view: CallsPresenter.View) : CallsPresenter {
    private val minCharacters: Int = 3

    override fun addComment(id: Int, text: String?) {
        if ((text != null) && text.length > minCharacters) {
            model.addComment(id, text)
            view.redirect()
        } else {
            view.displayNotFound()
        }
    }

}