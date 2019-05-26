package belabes.mohamed.cms.presenters.admin

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

interface AdminArticlePresenter {
    fun start(id: Int)

    interface View {
        fun displayArticle(article: Article, comments: List<Comment>)

        fun displayNotFound()
    }
}