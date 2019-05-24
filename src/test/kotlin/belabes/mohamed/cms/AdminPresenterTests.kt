package belabes.mohamed.cms

import belabes.mohamed.cms.admin.AdminArticleListPresenter
import belabes.mohamed.cms.admin.AdminArticlePresenter
import belabes.mohamed.cms.admin.AdminCallsPresenter
import belabes.mohamed.cms.control.admin.AdminArticleListPresenterImpl
import belabes.mohamed.cms.control.admin.AdminArticlePresenterImpl
import belabes.mohamed.cms.control.admin.AdminCallsPresenterImpl
import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

class AdminPresenterTests {

    /**
     * Tests the list of article that should be displayed on /admin/
     */
    @Test
    fun testAdminArticleListPresenter() {
        val listOf = listOf(
            Article(1, "Toto", "Tata"),
            Article(2, "Toto", "Tata")
        )

        val model = mock<Model> {
            on { getArticles() } doReturn listOf
        }

        val view = mock<AdminArticleListPresenter.View>()

        val presenter = AdminArticleListPresenterImpl(model, view)

        presenter.start()

        verify(model).getArticles()
        verify(view).displayArticleList(listOf)
        verifyNoMoreInteractions(model, view)
    }

    /**
     * Tests the details of an article that should be displayed on /admin/article/{id}
     */
    @Test
    fun testAdminArticlePresenter() {
        val article = Article(1, "Toto", "Tata")
        val comments: List<Comment> = listOf(
            Comment(1, 1, "tototo"),
            Comment(2, 1, "tototo"),
            Comment(3, 1, "tototo"),
            Comment(4, 1, "tototo")
        )

        val model = mock<Model> {
            on { getArticle(1) } doReturn article
            on { getArticleComments(1) } doReturn comments
        }

        val view = mock<AdminArticlePresenter.View>()

        val presenter = AdminArticlePresenterImpl(model, view)

        presenter.start(1)

        verify(model).getArticle(1)
        verify(model).getArticleComments(1)
        verify(view).displayArticle(article, comments)
        verifyNoMoreInteractions(model, view)
    }

    /**
     * Tests the action of adding a new article
     */
    @Test
    fun testAdminAddArticle() {
        val article = Article(1, "Article", "Article to be deleted")

        val model = mock<Model> { }

        val view = mock<AdminCallsPresenter.View>()

        val presenter = AdminCallsPresenterImpl(model, view)

        presenter.addArticle(article.title, article.text)

        // VERIFY
        verify(model).addArticle(article.title, article.text)
        verify(view).redirect()
        verifyNoMoreInteractions(model, view)
    }

    /**
     * Tests the action of deleting an existing article after deleting its related comments
     */
    @Test
    fun testAdminDeleteArticle() {
        val article = Article(1, "Article", "Article to be deleted")

        val model = mock<Model> { }

        val view = mock<AdminCallsPresenter.View>()

        val presenter = AdminCallsPresenterImpl(model, view)

        presenter.deleteArticle(article.id)

        // VERIFY
        verify(model).deleteCommentsByArticle(article.id)
        verify(model).deleteArticle(article.id)
        verify(view).redirect()
        verifyNoMoreInteractions(model, view)
    }

    /**
     * Tests the action of deleting one single comment
     */
    @Test
    fun testAdminDeleteComment() {
        val comment = object {
            val id: Int = 1
        }

        val model = mock<Model> { }

        val view = mock<AdminCallsPresenter.View>()

        val presenter = AdminCallsPresenterImpl(model, view)

        presenter.deleteComment(comment.id)

        // VERIFY
        verify(model).deleteComment(comment.id)
        verify(view).redirect()
        verifyNoMoreInteractions(model, view)
    }

    /**
     * Tests if the right views are displayed if no article is found
     */
    @Test
    fun testInvalidArticlePresenter() {
        val model = mock<Model> {
            on { getArticle(any()) } doReturn null
        }

        val view = mock<AdminArticlePresenter.View>()

        val presenter = AdminArticlePresenterImpl(model, view)

        presenter.start(42)

        verify(model).getArticle(42)
        verify(view).displayNotFound()
        verifyNoMoreInteractions(model, view)
    }
}