package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.beyond.Transient
import com.megacrit.cardcrawl.powers.FadingPower
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower
import javassist.expr.ExprEditor
import javassist.expr.NewExpr

@SpirePatch(
    clz = Transient::class,
    method = "usePreBattleAction"
)
class TransientBuffs {
    companion object {
        @JvmStatic
        fun Instrument(): ExprEditor =
            object : ExprEditor() {
                override fun edit(e: NewExpr) {
                    if (e.className == FadingPower::class.qualifiedName) {
                        e.replace("\$_ = \$proceed(\$1, \$2 + 1);")
                    }
                }
            }

        @JvmStatic
        fun Postfix(__instance: Transient) {
            AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, RegenerateMonsterPower(__instance, 999), 999))
        }
    }
}
