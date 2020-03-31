package com.evacipated.cardcrawl.mod.humilty.patches.city

import basemod.ReflectionHacks
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.MonsterHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.MonsterGroup
import com.megacrit.cardcrawl.monsters.city.GremlinLeader

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
                GremlinLeader.POSX[3] = -590f
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
}
