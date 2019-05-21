package belabes.mohamed.cms

import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment

class MysqlModel(private val pool: ConnectionPool) : Model {
    override fun postArticleComment(id: Int, text: String) {
        pool.useConnection { connection ->
            connection.prepareStatement("INSERT INTO comments (article_id, text) VALUES (?, ?)").use {stmt ->
                stmt.setInt(1, id)
                stmt.setString(2, text)
                stmt.executeUpdate()
            }
        }
    }

    override fun getArticles(): List<Article> {
        val articles = ArrayList<Article>()

        pool.useConnection { connection ->
            connection.prepareStatement("SELECT * FROM articles").use {stmt ->
                stmt.executeQuery().use {results ->
                    while (results.next()) {
                        articles += Article(
                            results.getInt("id"),
                            results.getString("title"),
                            results.getString("text")
                        )
                    }
                }
            }
        }

        return articles
    }

    override fun getArticle(id: Int): Article? {
        pool.useConnection { connection ->
            connection.prepareStatement("SELECT * FROM articles WHERE id = ?").use {stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use {result ->
                    if (result.next()) {
                        return Article(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("text")
                        )
                    }
                }
            }
        }
        return null
   }

    override fun getArticleComments(id: Int): List<Comment> {
        val comments = ArrayList<Comment>()

        pool.useConnection { connection ->
            connection.prepareStatement("SELECT * FROM comments WHERE article_id = ?").use {stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use {results ->
                    while (results.next()) {
                        comments += Comment (
                            results.getInt("id"),
                            results.getInt("article_id"),
                            results.getString("text")
                        )
                    }
                }
            }
        }

        return comments
    }
}