package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.mod.humilty.powers.CrushingBlowsPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob
import javassist.CannotCompileException
import javassist.CtBehavior
import javassist.CtNewMethod

@SpirePatch(
    clz = GremlinNob::class,
    method = SpirePatch.CONSTRUCTOR,
    paramtypez = [Float::class, Float::class, Boolean::class]
)
class NobWounds {
    companion object {
        @JvmStatic
        fun Raw(ctBehavior: CtBehavior) {
            val ctClass = ctBehavior.declaringClass
            val pool = ctClass.classPool
            var method = CtNewMethod.make(
                "void usePreBattleAction() {}",
                ctClass
            )
            try {
                ctClass.addMethod(method)
            } catch (e: CannotCompileException) {
                e.printStackTrace()
                method = ctClass.getDeclaredMethod("usePreBattleAction")
            }

            method.insertBefore("${NobWounds::class.qualifiedName}.doPreBattleAction(this);")
        }

        @JvmStatic
        fun doPreBattleAction(nob: GremlinNob) {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(nob, nob, CrushingBlowsPower(nob))
            )
        }
    }
}
