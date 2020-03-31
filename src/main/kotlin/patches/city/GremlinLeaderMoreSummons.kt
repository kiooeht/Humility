package com.evacipated.cardcrawl.mod.humilty.patches.city

import basemod.ReflectionHacks
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.unique.SummonGremlinAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.MonsterHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.MonsterGroup
import com.megacrit.cardcrawl.monsters.city.GremlinLeader
import javassist.CtBehavior

class GremlinLeaderMoreSummons {
    @SpirePatch(
        clz = GremlinLeader::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class MoreSummonSlots {
        companion object {
            @JvmStatic
            fun Postfix(__instance: GremlinLeader) {
                __instance.gremlins = __instance.gremlins.copyOf(__instance.gremlins.size + 1)
            }
        }
    }

    @SpirePatch(
        clz = GremlinLeader::class,
        method = SpirePatch.STATICINITIALIZER
    )
    class MoreSummonPositions {
        companion object {
            @JvmStatic
            fun Postfix() {
                ReflectionHacks.setPrivateStaticFinal(
                    GremlinLeader::class.java,
                    "POSX",
                    GremlinLeader.POSX.copyOf(GremlinLeader.POSX.size + 1)
                )
                ReflectionHacks.setPrivateStaticFinal(
                    GremlinLeader::class.java,
                    "POSY",
                    GremlinLeader.POSY.copyOf(GremlinLeader.POSY.size + 1)
                )
                GremlinLeader.POSX[3] = -690f
                GremlinLeader.POSY[3] = 2f
            }
        }
    }

    @SpirePatch(
        clz = MonsterHelper::class,
        method = "getEncounter"
    )
    class ExtraGremlin {
        companion object {
            @JvmStatic
            fun Postfix(__result: MonsterGroup, key: String): MonsterGroup {
                if (key == MonsterHelper.GREMLIN_LEADER_ENC) {
                    val spawnMonster = MonsterHelper::class.java.getDeclaredMethod("spawnGremlin", Float::class.java, Float::class.java).also { it.isAccessible = true }
                    __result.addMonster(2, spawnMonster.invoke(null, GremlinLeader.POSX[2], GremlinLeader.POSY[2]) as AbstractMonster)
                }
                return __result
            }
        }
    }

    @SpirePatch(
        clz = GremlinLeader::class,
        method = "usePreBattleAction"
    )
    class MoreStartingGremlins {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                rloc = 4
            )
            fun Insert(__instance: GremlinLeader) {
                __instance.gremlins[2] = AbstractDungeon.getMonsters().monsters[2]
                __instance.gremlins[3] = null
            }
        }
    }

    @SpirePatch(
        clz = GremlinLeader::class,
        method = "takeTurn"
    )
    class RallyMore {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: GremlinLeader) {
                AbstractDungeon.actionManager.addToBottom(SummonGremlinAction(__instance.gremlins))
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.NewExprMatcher(SummonGremlinAction::class.java)
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }

    @SpirePatch(
        clz = SummonGremlinAction::class,
        method = "getRandomGremlin"
    )
    class UseNewPosition {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class,
                localvars = ["x", "y"]
            )
            fun aasdf(__instance: SummonGremlinAction, slot: Int, @ByRef(type = "float") x: Array<Float>, @ByRef(type = "float") y: Array<Float>) {
                if (slot == 3) {
                    x[0] = GremlinLeader.POSX[3]
                    y[0] = GremlinLeader.POSY[3]
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.MethodCallMatcher(MonsterHelper::class.java, "getGremlin")
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }
}
