package belabes.mohamed.cms

import belabes.mohamed.cms.admin.AdminArticleListPresenter
import belabes.mohamed.cms.admin.AdminCallsPresenter
import belabes.mohamed.cms.model.Article
import belabes.mohamed.cms.model.Comment
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

data class AuthSession(val user: String)

fun main() {
    val component = AppComponents("jdbc:mysql://localhost:8889/CMS?serverTimezone=UTC", "root", "root")

    val server = embeddedServer(Netty, port = 8080) {

        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
        }

        install(Sessions) {
            cookie<AuthSession>("AUTH_SESSION", SessionStorageMemory())
        }

        //TODO: Peut etre, si jamais, on verra, BDD.

        install(Authentication) {
            form("equal-auth") {
                userParamName = "username"
                passwordParamName = "password"
                challenge = FormAuthChallenge.Redirect { "/login" }
                validate { credentials ->
                    if (credentials.name == credentials.password) {
                        UserIdPrincipal(credentials.name)
                    } else {
                        null
                    }
                }
                skipWhen { call -> call.sessions.get<AuthSession>() != null }
            }
        }

        routing {
            static ("static") {
                resources("static")
            }


            get("/article/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()

                var controller = component.getArticlePresenter(object : ArticlePresenter.View {
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

                if (id  == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    controller.start(id)
                }
            }

            post("/article/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                val postParameters: Parameters = call.receiveParameters()

                val text: String? = postParameters["text"]

                var controller = component.getArticlePresenter(object : ArticlePresenter.View {
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
                    controller.postComment(id, text)
                }
            }

            get ("/") {
                var controller = component.getArticleListPresenter(object : ArticleListPresenter.View {
                    override fun displayArticleList(list: List<Article>) {
                        val context = IndexContext(list)
                        launch {
                            call.respond(FreeMarkerContent("index.ftl", context, null))
                        }
                    }
                })

                controller.start()
            }

            // ADMIN
            get("/login") {
                call.respond(FreeMarkerContent("auth/login.ftl", null, null))
            }

            authenticate("equal-auth") {
                post("/login") {
                    val principal = call.authentication.principal<UserIdPrincipal>()
                    call.sessions.set(AuthSession(principal!!.name))
                    call.respondRedirect("/admin/")
                }

                route("admin") {
                    get {
                        var controller = component.getAdminArticleListPresenter(object : AdminArticleListPresenter.View {
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

                            var controller = component.getArticlePresenter(object : ArticlePresenter.View {
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

                            if (id  == null) {
                                call.respond(HttpStatusCode.NotFound)
                            } else {
                                controller.start(id)
                            }
                        }

                        get("delete/{id}") {
                            val id = call.parameters["id"]?.toIntOrNull()

                            var controller = component.getAdminCallsPresenter(object : AdminCallsPresenter.View {
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

                            var controller = component.getAdminCallsPresenter(object : AdminCallsPresenter.View {
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

                            if (title == null || text == null) {
                                call.respond(HttpStatusCode.NotFound)
                            } else {
                                controller.addArticle(title, text)
                            }
                        }

                        route("comment") {
                            get ("delete/{id}") {
                                val id = call.parameters["id"]?.toIntOrNull()

                                var controller = component.getAdminCallsPresenter(object : AdminCallsPresenter.View {
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
                    call.sessions.clear<AuthSession>()
                    call.respondRedirect("/")
                }
            }
        }
    }

    server.start(wait = true)
}