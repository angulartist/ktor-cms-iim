package belabes.mohamed.cms.control.admin

import belabes.mohamed.cms.Model
import belabes.mohamed.cms.presenters.admin.AdminArticleListPresenter

class AdminArticleListPresenterImpl(private val model: Model, private val view: AdminArticleListPresenter.View) :
    AdminArticleListPresenter {
    override fun start() {
        val list = model.getArticles()
        view.displayArticleList(list)
    }
}