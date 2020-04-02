package com.evacipated.cardcrawl.mod.humilty.patches.events

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.evacipated.cardcrawl.mod.humilty.patches.utils.addToMethod
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.beyond.MindBloom
import com.megacrit.cardcrawl.helpers.MonsterHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import javassist.CtBehavior
import javassist.expr.ExprEditor
import javassist.expr.MethodCall

class MindBloomDoubleBoss {
    companion object {
        private var secondBoss: String? = null
    }

    @SpirePatch(
        clz = MindBloom::class,
        method = "buttonEffect"
    )
    class SaveSecondBoss {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class,
                localvars = ["list"]
            )
            fun Insert(__instance: MindBloom, buttonPressed: Int, list: ArrayList<String>) {
                if (list.size > 1) {
                    secondBoss = list[1]
                    AbstractDungeon.getCurrRoom().rewardAllowed = false
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.MethodCallMatcher(MonsterHelper::class.java, "getEncounter")
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }

    @SpirePatch(
        clz = MindBloom::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class GotoDoubleBoss {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addToMethod("reopen", ::reopen)
            }

            @JvmStatic
            fun reopen(__instance: MindBloom) {
                if (secondBoss != null) {
                    AbstractDungeon.resetPlayer()
                    AbstractDungeon.player.drawX = Settings.WIDTH * 0.25f
                    AbstractDungeon.player.preBattlePrep()

                    AbstractDungeon.getCurrRoom().isBattleOver = false
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(secondBoss)
                    secondBoss = null
                    AbstractDungeon.getCurrRoom().rewardAllowed = true
                    __instance.enterCombatFromImage()
                }
            }
        }
    }

    @SpirePatch(
        clz = AbstractMonster::class,
        method = "onBossVictoryLogic"
    )
    class StopMusicFade {
        companion object {
            @JvmStatic
            fun Instrument(): ExprEditor =
                object : ExprEditor() {
                    override fun edit(m: MethodCall) {
                        if (m.methodName == "silenceTempBgmInstantly" || m.methodName == "silenceBGMInstantly" || m.methodName == "playBossStinger") {
                            m.replace("if (!${StopMusicFade::class.qualifiedName}.isInDoubleBoss())" +
                                    "{ \$_ = \$proceed(\$\$); }")
                        }
                    }
                }

            @JvmStatic
            fun isInDoubleBoss(): Boolean {
                return AbstractDungeon.getCurrMapNode()?.getRoom()?.event is MindBloom && secondBoss != null
            }
        }
    }

    @SpirePatch(
        clz = MindBloom::class,
        method = SpirePatch.STATICINITIALIZER
    )
    class ChangeIAmWarText {
        companion object {
            @JvmStatic
            fun Postfix() {
                MindBloom.OPTIONS[0] = CardCrawlGame.languagePack.getEventString(HumilityMod.makeID(MindBloom.ID))?.OPTIONS?.get(0) ?: MindBloom.OPTIONS[0]
            }
        }
    }
}
