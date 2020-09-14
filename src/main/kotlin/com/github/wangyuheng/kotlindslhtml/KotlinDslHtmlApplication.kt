package com.github.wangyuheng.kotlindslhtml

import io.javalin.Javalin
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun main(args: Array<String>) {
    val app = Javalin.create().start(17000)

    val tableHeaders = arrayListOf("First", "Last", "Handle")
    val tableRows = arrayListOf(
            arrayListOf("Mark", "Otto", "@mdo"),
            arrayListOf("Jacob", "Thornton", "@fat"),
            arrayListOf("Larry", "the Bird", "@twitter")
    )


    val list = arrayListOf("Action", "Another action", "Something else here")
    app.get("/dropdown") {ctx->

        ctx.html(createHTML()

                .html {
                    head {
                        b4()
                    }
                    body {
                        b4table(tableHeaders, tableRows.map { r ->
                            r.map {
                                { +it }
                            }
                        }, false)
                        div {
                            b4dropdown("Dropdown link") {
                                list.forEach {
                                    b4dropdownItem(it)
                                }
                            }
                        }
                    }
                })}

        app.get("/") {
            it.html(
                    createHTML()
                            .html {
                                val tableRowsUnit = tableRows.map { r ->
                                    r.map {
                                        { +it }
                                    }
                                }
                                head {
                                    b4()
                                }
                                body {
                                    b4table(tableHeaders, tableRowsUnit, false)
                                    div {
                                        b4dropdown("Dropdown link") {
                                            list.forEach { b4dropdownItem(it) }
                                        }
                                    }
                                    div {
                                        b4table(
                                                tableHeaders.toList().plus("action"),
                                                tableRowsUnit.map { r ->
                                                    r.plus {
                                                        ul {
                                                            lia("#") { +"Edit" }
                                                            lia("#") { +"Delete" }
                                                        }
                                                    }
                                                },
                                                true
                                        )
                                    }
                                }
                            }
            )
    }
}

fun UL.lia(href: String, block: A.() -> Unit) {
    li {
        a(href) { block() }
    }
}

fun HEAD.b4() {
    link {
        href = "https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
        rel = "stylesheet"
    }
    script {
        src = "https://code.jquery.com/jquery-3.5.1.slim.min.js"
    }
    script {
        src = "https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
    }
    script {
        src = "https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
    }
}

/**
 *  <div class="dropdown">
 *      <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
 *      Dropdown link
 *      </a>
 *      <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
 *          <a class="dropdown-item" href="#">Action</a>
 *          <a class="dropdown-item" href="#">Another action</a>
 *          <a class="dropdown-item" href="#">Something else here</a>
 *      </div>
 *  </div>
 */
fun DIV.b4dropdown(btn: String, block: DIV.() -> Unit) {
    div("dropdown") {
        a("#") {
            classes = setOf("btn", "btn-secondary", "dropdown-toggle")
            role = "button"
            attributes["aria-expanded"] = "false"
            attributes["data-toggle"] = "dropdown"
            +btn
        }
        div("dropdown-menu") {
            attributes["aria-labelledby"] = "dropdownMenuLink"
            block()
        }
    }
}

fun DIV.b4dropdownItem(content: String, href: String? = "#", target: String? = null) {
    a(href, target, "dropdown-item") { +content }
}

fun HtmlBlockTag.b4table(headers: List<String>, rows: List<List<() -> Unit>>, showIndex: Boolean = false) {
    table("table") {
        thead {
            tr {
                if (showIndex) th(ThScope.col) { +"#" }
                headers.forEach { th(ThScope.col) { +it } }
            }
        }
        tbody {
            for ((index, row) in rows.withIndex()) {
                tr {
                    if (showIndex) td { +"${index + 1}" }
                    row.forEach { td { it() } }
                }
            }
        }
    }
}