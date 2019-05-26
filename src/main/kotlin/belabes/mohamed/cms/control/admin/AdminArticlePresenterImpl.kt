package belabes.mohamed.cms.control.admin

import belabes.mohamed.cms.Model
import belabes.mohamed.cms.presenters.admin.AdminArticlePresenter
import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

class AdminArticlePresenterImpl(private val model: Model, private val view: AdminArticlePresenter.View) :
    AdminArticlePresenter {
    override fun start(id: Int) {
        val article: Article? = model.getArticle(id)
        if (article != null) {
            val comments: List<Comment> = model.getArticleComments(id)
            view.displayArticle(article, comments)
        } else {
            view.displayNotFound()
        }
    }
}