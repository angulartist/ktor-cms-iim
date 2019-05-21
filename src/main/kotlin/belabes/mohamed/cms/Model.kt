package belabes.mohamed.cms

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

interface Model {
    fun getArticles(): List<Article>

    fun getArticle(id: Int): Article?

    fun getArticleComments(id: Int): List<Comment>

    fun postArticleComment(id: Int, text: String)
}