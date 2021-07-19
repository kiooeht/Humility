package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.evacipated.cardcrawl.mod.humilty.powers.PainfulPunchesPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.MonsterStrings
import com.megacrit.cardcrawl.monsters.city.BookOfStabbing
import com.megacrit.cardcrawl.powers.PainfulStabsPower
import javassist.expr.ExprEditor
import javassist.expr.NewExpr

class BookOfPunching {
    companion object {
        private val strings: MonsterStrings by lazy { CardCrawlGame.languagePack.getMonsterStrings(HumilityMod.makeID(BookOfStabbing.ID)) }
    }

    @SpirePatch(
        clz = BookOfStabbing::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class Name {
        companion object {
            @JvmStatic
            fun Postfix(__instance: BookOfStabbing) {
                __instance.name = strings.NAME
            }
        }
    }

    @SpirePatch(
        clz = BookOfStabbing::class,
        method = "usePreBattleAction"
    )
    class PunchPower {
        companion object {
            @JvmStatic
            fun Instrument(): ExprEditor =
                object : ExprEditor() {
                    override fun edit(e: NewExpr) {
                        if (e.className == PainfulStabsPower::class.qualifiedName) {
                            e.replace("\$_ = new ${PainfulPunchesPower::class.qualifiedName}(\$\$);")
                        }
                    }
                }
        }
    }
}
