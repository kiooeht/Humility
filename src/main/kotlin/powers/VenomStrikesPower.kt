package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature

class VenomStrikesPower(
    owner: AbstractCreature,
    amount: Int
) : AbstractHumilityPower(POWER_ID, "envenom") {
    companion object {
        val POWER_ID = HumilityMod.makeID("VenomStrikes")
    }

    init {
        this.owner = owner
        this.amount = amount
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(amount)
    }

    override fun onInflictDamage(info: DamageInfo, damageAmount: Int, target: AbstractCreature) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS) {
            addToTop(ApplyPowerAction(target, owner, VenomPower(target, owner, amount), amount, true))
        }
    }
}
