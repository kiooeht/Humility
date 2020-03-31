package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.helpers.FontHelper

class DecasProtectionPower(
    owner: AbstractCreature,
    private val linked: AbstractCreature
) : AbstractHumilityPower(POWER_ID, "heartDef") {
    companion object {
        val POWER_ID = HumilityMod.makeID("DecasProtection")
        private const val PERCENT_DR = 50
    }

    init {
        this.owner = owner
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(PERCENT_DR, FontHelper.colorString(linked.name, "y"))
    }

    override fun atDamageFinalReceive(damage: Float, type: DamageInfo.DamageType): Float {
        return damage * ((100 - PERCENT_DR) / 100f)
    }
}
