package belabes.mohamed.cms.presenters.admin

import belabes.mohamed.cms.model.Article

interface AdminArticleListPresenter {
    fun start()

    interface View {
        fun displayArticleList(list: List<Article>)
    }
}