package belabes.mohamed.cms

import belabes.mohamed.cms.presenters.admin.AdminArticleListPresenter
import belabes.mohamed.cms.presenters.admin.AdminCallsPresenter
import belabes.mohamed.cms.control.ArticleListPresenterImpl
import belabes.mohamed.cms.control.ArticlePresenterImpl
import belabes.mohamed.cms.control.CallsPresenterImpl
import belabes.mohamed.cms.control.admin.AdminArticleListPresenterImpl
import belabes.mohamed.cms.control.admin.AdminCallsPresenterImpl
import belabes.mohamed.cms.presenters.ArticleListPresenter
import belabes.mohamed.cms.presenters.ArticlePresenter
import belabes.mohamed.cms.presenters.CallsPresenter
import belabes.mohamed.cms.services.AuthService

class AppComponents(mysqlUrl: String, mysqlUser: String, mysqlPassword: String) {
    private val pool = ConnectionPool(mysqlUrl, mysqlUser, mysqlPassword)

    private val model = MysqlModel(getPool())

    private fun getPool(): ConnectionPool = pool

    private fun getModel(): Model = model

    fun getArticleListPresenter(view: ArticleListPresenter.View): ArticleListPresenter = ArticleListPresenterImpl(getModel(), view)

    fun getArticlePresenter(view: ArticlePresenter.View): ArticlePresenter = ArticlePresenterImpl(getModel(), view)

    fun getCallsPresenter(view: CallsPresenter.View): CallsPresenter = CallsPresenterImpl(getModel(), view)

    // ADMIN STUFF

    fun getAdminArticleListPresenter(view: AdminArticleListPresenter.View): AdminArticleListPresenter = AdminArticleListPresenterImpl(getModel(), view)

    fun getAdminCallsPresenter(view: AdminCallsPresenter.View): AdminCallsPresenter = AdminCallsPresenterImpl(getModel(), view)

    // AUTH STUFF

    val authService = AuthService(getModel())
}