package belabes.mohamed.cms

import belabes.mohamed.cms.model.Article

interface ArticleListPresenter {
    fun start()

    interface View {
        fun displayArticleList(list: List<Article>)
    }
}