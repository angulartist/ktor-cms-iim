package belabes.mohamed.cms.presenters

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

interface ArticlePresenter {
    fun start(id: Int)

    interface View {
        fun displayArticle(article: Article, comments: List<Comment>)

        fun displayNotFound()

        fun displayBadFormat()
    }
}