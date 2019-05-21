package belabes.mohamed.cms.control

import belabes.mohamed.cms.ArticleListPresenter
import belabes.mohamed.cms.Model

class ArticleListPresenterImpl(private val model: Model, private val view: ArticleListPresenter.View) : ArticleListPresenter {
    override fun start() {
        val list = model.getArticles()
        view.displayArticleList(list)
    }
}