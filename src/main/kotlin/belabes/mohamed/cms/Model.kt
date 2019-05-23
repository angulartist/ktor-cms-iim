package belabes.mohamed.cms

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment
import belabes.mohamed.cms.model.User

interface Model {
    fun getArticles(): List<Article>

    fun getArticle(id: Int): Article?

    fun deleteArticle(id: Int)

    fun deleteCommentsByArticle(id: Int)

    fun deleteComment(id: Int)

    fun getArticleComments(id: Int): List<Comment>

    fun addArticle(title: String, text: String)

    fun addComment(id: Int, text: String)

    fun getUserByUsername(username: String): User?
}