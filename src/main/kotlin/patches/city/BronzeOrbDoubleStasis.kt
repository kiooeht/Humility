package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.unique.ApplyStasisAction
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.city.BronzeOrb
import com.megacrit.cardcrawl.powers.StasisPower
import javassist.CtBehavior
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

class BronzeOrbDoubleStasis {
    @SpirePatch(
        clz = BronzeOrb::class,
        method = "takeTurn"
    )
    class TakeTurn {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: BronzeOrb) {
                AbstractDungeon.actionManager.addToBottom(ApplyStasisAction(__instance))
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.NewExprMatcher(ApplyStasisAction::class.java)
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }

    @SpirePatch(
        clz = StasisPower::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class NonStackableStasis {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                val ctClass = ctBehavior.declaringClass
                ctClass.addInterface(ctClass.classPool.get(NonStackablePower::class.qualifiedName))
            }
        }
    }

    @SpirePatch2(
        clz = ApplyStasisAction::class,
        method = "update"
    )
    class FixStasisLastCard {
        companion object {
            @JvmStatic
            fun Instrument(): ExprEditor =
                object : ExprEditor() {
                    override fun edit(m: MethodCall) {
                        if (m.className == CardGroup::class.qualifiedName && m.methodName == "isEmpty") {
                            m.replace(
                                "\$_ = \$proceed(\$\$) && card == null;"
                            )
                        }
                    }
                }
        }
    }
}
