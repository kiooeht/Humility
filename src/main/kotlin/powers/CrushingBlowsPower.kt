package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Wound
import com.megacrit.cardcrawl.core.AbstractCreature

class CrushingBlowsPower(
    owner: AbstractCreature
) : AbstractHumilityPower(POWER_ID, "painfulStabs") {
    companion object {
        val POWER_ID = HumilityMod.makeID("CrushingBlows")
    }

    init {
        this.owner = owner
        this.amount = -1
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0]
    }

    override fun onInflictDamage(info: DamageInfo?, damageAmount: Int, target: AbstractCreature?) {
        if (damageAmount > 0 && info?.type != DamageInfo.DamageType.THORNS) {
            addToBot(MakeTempCardInDiscardAction(Wound(), 1))
        }
    }
}
