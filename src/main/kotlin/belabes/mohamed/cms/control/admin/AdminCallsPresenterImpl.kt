package belabes.mohamed.cms.control.admin

import belabes.mohamed.cms.Model
import belabes.mohamed.cms.presenters.admin.AdminCallsPresenter

class AdminCallsPresenterImpl(private val model: Model, private val view: AdminCallsPresenter.View) :
    AdminCallsPresenter {
    override fun addArticle(title: String, text: String) {
        model.addArticle(title, text)
        view.redirect()
    }

    override fun deleteArticle(id: Int) {
        model.deleteCommentsByArticle(id)
        model.deleteArticle(id)
        view.redirect()
    }

    override fun deleteComment(id: Int) {
        model.deleteComment(id)
        view.redirect()
    }
}