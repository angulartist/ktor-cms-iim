package belabes.mohamed.cms.control

import belabes.mohamed.cms.ArticlePresenter
import belabes.mohamed.cms.Model
import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

class ArticlePresenterImpl(private val model: Model, private val view: ArticlePresenter.View) : ArticlePresenter {
    override fun start(id: Int) {
        val article: Article? = model.getArticle(id)
        val comments: List<Comment> = model.getArticleComments(id)

        if (article != null) {
            view.displayArticle(article, comments)
        } else {
            view.displayNotFound()
        }
    }
}