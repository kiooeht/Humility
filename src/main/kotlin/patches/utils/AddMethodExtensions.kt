package com.evacipated.cardcrawl.mod.humilty.patches.utils

import com.megacrit.cardcrawl.monsters.AbstractMonster
import javassist.CtBehavior
import javassist.CtNewMethod
import javassist.bytecode.DuplicateMemberException
import kotlin.reflect.KFunction1
import kotlin.reflect.jvm.javaMethod

fun <T : AbstractMonster> CtBehavior.addPreBattleAction(callback: KFunction1<T, Unit>) {
    val method = callback.javaMethod!!
    this.addToMethod(
        "usePreBattleAction",
        "${method.declaringClass.declaringClass.name}.${method.name}(this);"
    )
}

fun <T : AbstractMonster> CtBehavior.addEscape(callback: KFunction1<T, Unit>) {
    val method = callback.javaMethod!!
    this.addToMethod(
        "escape",
        "${method.declaringClass.declaringClass.name}.${method.name}(this);"
    )
}

fun CtBehavior.addToMethod(methodName: String, src: String) {
    var method = CtNewMethod.delegator(
        declaringClass.superclass.getDeclaredMethod(methodName),
        declaringClass
    )
    try {
        declaringClass.addMethod(method)
    } catch (e: DuplicateMemberException) {
        method = declaringClass.getDeclaredMethod(methodName)
    }

    method.insertAfter(src)
}
