package belabes.mohamed.cms.presenters.admin

interface AdminCallsPresenter {
    fun addArticle(title: String, text: String)

    fun deleteArticle(id: Int)

    fun deleteComment(id: Int)

    interface View {
        fun redirect()

        fun displayNotFound()
    }
}