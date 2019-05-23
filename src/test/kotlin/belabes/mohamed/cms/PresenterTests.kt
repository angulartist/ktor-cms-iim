package belabes.mohamed.cms

import belabes.mohamed.cms.control.ArticleListPresenterImpl
import belabes.mohamed.cms.control.ArticlePresenterImpl
import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

class PresenterTests {

    @Test
    fun testArticleListPresenter(){
        /* Given */
        val listOf = listOf(
            Article(1, "Toto", "Tata"),
            Article(2, "Toto", "Tata")
        )

        val model = mock<Model> {
            on { getArticles() } doReturn listOf
        }

        val view = mock<ArticleListPresenter.View>()

        val presenter = ArticleListPresenterImpl(model, view)

        presenter.start()

        verify(model).getArticles()
        verify(view).displayArticleList(listOf)
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun testArticlePresenter() {
        val article = Article(1, "Toto", "Tata")
        val comments: List<Comment> = listOf(
            Comment(1, 1, "tototo"),
            Comment(2, 1, "tototo"),
            Comment(3, 1, "tototo"),
            Comment(4, 1, "tototo")
        )

        val model = mock<Model> {
            on { getArticle(1) } doReturn article
        }

        val view = mock<ArticlePresenter.View>()

        val presenter = ArticlePresenterImpl(model, view)

        presenter.start(1)

        verify(model).getArticle(1)
        verify(view).displayArticle(article, comments)
        verify(view, never()).displayNotFound()
        verifyNoMoreInteractions(model, view)
    }

    @Test
    fun testInvalidArticlePresenter() {

        val model = mock<Model> {
            on { getArticle(any()) } doReturn null
        }

        val view = mock<ArticlePresenter.View>()

        val presenter = ArticlePresenterImpl(model, view)

        presenter.start(42)

        verify(model).getArticle(42)
        verify(view).displayNotFound()
        verifyNoMoreInteractions(model, view)
    }
}