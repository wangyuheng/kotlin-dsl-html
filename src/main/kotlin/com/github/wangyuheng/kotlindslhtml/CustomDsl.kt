package com.github.wangyuheng.kotlindslhtml

enum class Operator {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE
}

class Expression {
    var source: String? = null
    var target: String? = null
    var operator: Operator? = null

    internal var before: () -> Unit = { }

    fun onBefore(onBefore: () -> Unit) {
        before = onBefore
    }

    fun execute(): String {
        this.before()
        val result = "$source $operator $target"
        println(result)
        return result
    }
}

fun expression(init: Expression.() -> Unit) {
    val wrap = Expression()
    wrap.init()

    wrap.execute()
}