# Humility
Humility is a difficulty mod that buffs (almost) every enemy in Slay the Spire while avoiding simple number buffs like increased HP or damage.
## Changelist

### Exordium

#### Normal Enemies
* Cultist
	* Attack also adds a Void into the discard pile
* Gremlin (Fat)
	* Starts with 3 times more HP than usual
	* Starts with Slow
* Gremlin (Mad)
	* Anger replaced with Strength Up (gain Strength each turn)
* Gremlin (Shield)
	* Gives 1 Buffer instead of Block
* Gremlin (Sneaky)
	* Starts with 10 Thievery
	* Escapes on turn 4
* Gremlin (Wizard)
	* Takes 1 fewer turns to charge up attack
* Jaw Worm
	* Always in Hard Mode
* Large Slimes
	* Splits at 75% HP instead of 50%
* Looter
	* Skips Smoke Bomb prep turn
* Louse
	* Regains Curl Up after uncurling
* Slaver (Blue)
	* New Move: Trip
		* Big Debuff
		* Applies No block to player
* Slaver (Red)
	* Cannot use Entangle on the same turn Blue Slaver uses Trip (see above)

#### Elites
* Gremlin Nob
	* Starts with Crushing Blow
	* Crushing Blow is identical to Painful Stabs
* Lagavulin
	* Wakes up at the end of turn 1 instead of turn 3
* Sentries
	* A 4th Sentry is added to the fight

#### Bosses
* The Guardian
	* Skips first Offensive-Defensive cycle, starting combat in Defensive Mode about to perform Twin Slam
* Hexaghost
	* Name changed to Pentaghost
	* Skips turn 1 Activate, starts combat using Divider
	* Divider deals H x 5 damage instead of H x 6
	* H is calculated as `Current HP / 10 + 1` instead of `Current HP / 12 + 1`
	* Skips the last Sear in its move rotation, cutting the move rotation before Inferno down to 5 turns instead of 6 turns
* Slime Boss
	* Splits at 75% HP instead of 50%

### City

#### Normal Enemies
* Byrds
	* Start with 1 Strength
* Centurion and Mystic
	* Centurion can only take 10 damage per turn while Mystic is alive
* Chosen
	* Hex now shuffles a Dazed into the draw pile whenever you play ANY card
* Fungi Beast
	* Spore Cloud goes off automatically after 2-4 turns as well as on death
* Mugger
	* Skips Smoke Bomb prep turn
* Shelled Parasite
	* Plated Armor is replaced with Metallicize
* Snake Plant
	* New Move:
		* Attack + Debuff
		* 12 damage
		* Applies -3 Strength and 3 Shackled to player
* Snecko
	* Starts with 1 Snecko Bot
	* Snecko Bot:
		* Whenever you play a card, choose a random number from 0 to 3. If the number is equal to the cost of the card played, gains X Strength.
* Spheric Guardian
	* Starts with 3 Buffer

#### Elites
* Book of Stabbing
	* Maximum number of gremlin minions increased to 4
* Gremlin Leader
	* Spawns up to 3 gremlins at a time with Rally
* Taskmaster
	* Attack is changed to 7 x N, where N = turn / 2 rounded up

#### Bosses
* Bronze Automaton
	* Skips spawn turn, starts combat with Bronze Orbs already spawned
* The Champ
	* Starts with 3 Strength
	* Cannot use Defensive Stance move
	* Gloat and Anger moves give 1 additional Strength
* The Collector
	* Maximum number of Torch Heads increased o 3
	* Spawns up to 3 Torch Heads at a time

### Beyond

#### Normal Enemies
* Darklings
	* A 4th Darkling is added to the fight
* The Maw
	* Always uses Slam on turn 2
	* Starts with 2 Strength
* Orb Walker
	* Starts with 2 Artifact
* Shapes (Exploder)
	* Starts with -1 turn on Explode
* Shapes (Repulsor)
	* On death, shuffles 2 Dazed into the draw pile
* Shapes (Spiker
	* Gives ALL monsters 2 Thorns when buffing
* Spire Growth
	* Constrict move (Big Debuff) changed to also deal damage equal to Quick Tackle
* Transient
	* Starts with +1 Fading
	* Starts with 999 Regenerate
* Writhing Mass
	* Starts with 8 Regenerate

#### Elites
* Giant Head
	* Remove Slow
* Nemesis
	* On non-Intangible turns, has Semi-Intangible
	* Semi-Intangible: Reduces damage received by 5
* Reptomancer
	* Retomancer and Daggers apply 2 Venom when dealing unblocked damage
	* Venom:
		* Poison but blockable

#### Bosses
* Awakened One
	* Added a third phase:
		* 150 Max HP (170 on Asc 9+)
		* Gains 10 Ritual
* Donu and Deca
	* Donu receives 50% less damage while Deca is alive
* Time Eater
	* Starts with Double Time
	* Double Time:
		* Whenever you play 6 cards, gains 1 Strength

### Ending

#### Elite
* Spire Shield and Spire Spear
	* Both start with Barricade

#### Boss
* Corrupt Heart
	* Debilitate move also applies -1 draw per turn and -1 energy per turn
