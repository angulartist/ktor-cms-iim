package belabes.mohamed.cms

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

interface ArticlePresenter {
    fun start(id: Int)

    fun postComment(id: Int, text: String?)

    interface View {
        fun displayArticle(article: Article, comments: List<Comment>)

        fun displayNotFound()

        fun displayBadFormat()
    }
}