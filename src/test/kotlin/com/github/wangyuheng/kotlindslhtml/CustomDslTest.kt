package com.github.wangyuheng.kotlindslhtml

import org.junit.Test

class CustomDslTest {

    @Test
    fun exec_expression() {
        val lambdaExpression: Expression.() -> Unit = {
            source = "a"
            target = "b"
            operator = Operator.ADD
            onBefore { println("before $source") }
        }
        expression(lambdaExpression)
    }

    @Test
    fun exec_expression_with_abbr() {
        expression {
            source = "a"
            target = "b"
            operator = Operator.ADD
            onBefore { println("before $source") }
        }
    }

}