package belabes.mohamed.cms.presenters

interface CallsPresenter {
    fun addComment(id: Int, text: String?)

    interface View {
        fun redirect()

        fun displayNotFound()
    }
}