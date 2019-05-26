package belabes.mohamed.cms

import belabes.mohamed.cms.presenters.admin.AdminArticleListPresenter
import belabes.mohamed.cms.presenters.admin.AdminCallsPresenter
import belabes.mohamed.cms.model.AppSession
import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment
import belabes.mohamed.cms.model.User
import belabes.mohamed.cms.presenters.ArticleListPresenter
import belabes.mohamed.cms.presenters.ArticlePresenter
import belabes.mohamed.cms.presenters.CallsPresenter
import belabes.mohamed.cms.tpl.DetailsContext
import belabes.mohamed.cms.tpl.IndexContext
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import kotlinx.coroutines.launch

class App

fun main() {
    val component = AppComponents("jdbc:mysql://localhost:8889/CMS?serverTimezone=UTC", "root", "root")

    val server = embeddedServer(Netty, port = 8080) {

        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }

        install(Sessions) {
            cookie<AppSession>("APP_SESSION_SSM", SessionStorageMemory())
        }

        install(Authentication) {
            form("check-auth") {
                userParamName = "username"
                passwordParamName = "password"
                challenge = FormAuthChallenge.Redirect { "/login" }

                validate { credentials ->
                    val authService = component.authService
                    val user: User? = authService.getUserByUsername(credentials.name)

                    if (user != null && (credentials.name == user.username) && (credentials.password == user.password)) {
                        UserIdPrincipal(credentials.name)
                    } else {
                        null
                    }
                }
                skipWhen { call -> call.sessions.get<AppSession>() != null }
            }
        }

        routing {
            static("static") {
                resources("static")
            }

            get("/") {
                val controller: ArticleListPresenter =
                    component.getArticleListPresenter(object : ArticleListPresenter.View {
                        override fun displayArticleList(list: List<Article>) {
                            val context = IndexContext(list)
                            launch {
                                call.respond(FreeMarkerContent("index.ftl", context, null))
                            }
                        }
                    })

                controller.start()
            }

            // ARTICLES

            route("article/{id}") {
                get {
                    val id = call.parameters["id"]?.toIntOrNull()

                    val controller: ArticlePresenter = component.getArticlePresenter(object : ArticlePresenter.View {
                        override fun displayArticle(article: Article, comments: List<Comment>) {
                            val context = DetailsContext(article, comments)
                            launch {
                                call.respond(FreeMarkerContent("details.ftl", context, null))
                            }
                        }

                        override fun displayNotFound() {
                            launch {
                                call.respond(HttpStatusCode.NotFound)
                            }
                        }

                        override fun displayBadFormat() {
                            launch {
                                call.respond(FreeMarkerContent("errors/format.ftl", null, null))
                            }
                        }
                    })

                    if (id == null) {
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        controller.start(id)
                    }
                }

                post("/comment") {
                    val id = call.parameters["id"]?.toIntOrNull()
                    val postParameters: Parameters = call.receiveParameters()

                    val text: String? = postParameters["text"]

                    val controller = component.getCallsPresenter(object: CallsPresenter.View {
                        override fun redirect() {
                            launch {
                                call.respondRedirect("/article/$id")
                            }
                        }

                        override fun displayNotFound() {
                            launch {
                                call.respond(HttpStatusCode.NotFound)
                            }
                        }
                    })

                    if (id == null) {
                        call.respond(HttpStatusCode.NotFound)
                    } else {
                        controller.addComment(id, text)
                    }
                }
            }

            // ADMIN
            get("/login") {
                call.respond(FreeMarkerContent("auth/login.ftl", null, null))
            }

            authenticate("check-auth") {
                post("/login") {
                    val principal = call.authentication.principal<UserIdPrincipal>()
                    call.sessions.set(AppSession(principal!!.name))
                    call.respondRedirect("/admin/")
                }

                route("admin") {
                    get {
                        val controller =
                            component.getAdminArticleListPresenter(object : AdminArticleListPresenter.View {
                                override fun displayArticleList(list: List<Article>) {
                                    val context = IndexContext(list)
                                    launch {
                                        call.respond(FreeMarkerContent("admin/admin_index.ftl", context, null))
                                    }
                                }
                            })

                        controller.start()
                    }

                    route("article") {
                        get("/{id}") {
                            val id = call.parameters["id"]?.toIntOrNull()

                            val controller: ArticlePresenter =
                                component.getArticlePresenter(object : ArticlePresenter.View {
                                    override fun displayArticle(article: Article, comments: List<Comment>) {
                                        val context = DetailsContext(article, comments)
                                        launch {
                                            call.respond(FreeMarkerContent("admin/admin_details.ftl", context, null))
                                        }
                                    }

                                    override fun displayNotFound() {
                                        launch {
                                            call.respond(HttpStatusCode.NotFound)
                                        }
                                    }

                                    override fun displayBadFormat() {
                                        launch {
                                            call.respond(FreeMarkerContent("errors/format.ftl", null, null))
                                        }
                                    }
                                })

                            if (id == null) {
                                call.respond(HttpStatusCode.NotFound)
                            } else {
                                controller.start(id)
                            }
                        }

                        get("delete/{id}") {
                            val id = call.parameters["id"]?.toIntOrNull()

                            val controller: AdminCallsPresenter =
                                component.getAdminCallsPresenter(object : AdminCallsPresenter.View {
                                    override fun redirect() {
                                        launch {
                                            call.respondRedirect("/admin/")
                                        }
                                    }

                                    override fun displayNotFound() {
                                        launch {
                                            call.respond(HttpStatusCode.NotFound)
                                        }
                                    }
                                })

                            if (id == null) {
                                call.respond(HttpStatusCode.NotFound)
                            } else {
                                controller.deleteArticle(id)
                            }
                        }

                        post("add") {
                            val postParameters: Parameters = call.receiveParameters()
                            val title: String? = postParameters["title"]
                            val text: String? = postParameters["text"]

                            val controller: AdminCallsPresenter =
                                component.getAdminCallsPresenter(object : AdminCallsPresenter.View {
                                    override fun redirect() {
                                        launch {
                                            call.respondRedirect("/admin/")
                                        }
                                    }

                                    override fun displayNotFound() {
                                        launch {
                                            call.respond(HttpStatusCode.NotFound)
                                        }
                                    }
                                })

                            if (title == null || title.isEmpty() || text == null || text.isEmpty()) {
                                call.respond(FreeMarkerContent("errors/format.ftl", null, null))
                            } else {
                                controller.addArticle(title, text)
                            }
                        }

                        route("comment") {
                            get("delete/{id}") {
                                val id = call.parameters["id"]?.toIntOrNull()

                                val controller: AdminCallsPresenter =
                                    component.getAdminCallsPresenter(object : AdminCallsPresenter.View {
                                        override fun redirect() {
                                            launch {
                                                call.respondRedirect("/admin/")
                                            }
                                        }

                                        override fun displayNotFound() {
                                            launch {
                                                call.respond(HttpStatusCode.NotFound)
                                            }
                                        }
                                    })

                                if (id == null) {
                                    call.respond(HttpStatusCode.NotFound)
                                } else {
                                    controller.deleteComment(id)
                                }
                            }
                        }
                    }
                }

                get("/logout") {
                    call.sessions.clear<AppSession>()
                    call.respondRedirect("/")
                }
            }
        }
    }

    server.start(wait = true)
}