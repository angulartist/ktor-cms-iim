package belabes.mohamed.cms

import belabes.mohamed.cms.control.CallsPresenterImpl
import belabes.mohamed.cms.model.Comment
import belabes.mohamed.cms.presenters.CallsPresenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.jupiter.api.Test

class CallsPresenterTests {
    /**
     * Tests the action of commenting an article
     */
    @Test
    fun testAddComment() {
        val comment = Comment(1, 2, "Comment blablabla")

        val model = mock<Model> { }

        val view = mock<CallsPresenter.View>()

        val presenter = CallsPresenterImpl(model, view)

        presenter.addComment(comment.article_id, comment.text)

        // VERIFY
        verify(model).addComment(comment.article_id, comment.text)
        verify(view).redirect()
        verifyNoMoreInteractions(model, view)
    }

    /**
     * Test adding an invalid comment
     */
    @Test
    fun testInvalidAddComment() {

        val model = mock<Model> { }

        val view = mock<CallsPresenter.View>()

        val presenter = CallsPresenterImpl(model, view)

        presenter.addComment(42, "")

        verify(view).displayNotFound()
        verifyNoMoreInteractions(model, view)
    }
}