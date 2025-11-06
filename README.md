# ⚖️ EasyKarma

A dynamic karma system for Minecraft servers — where every action has a consequence.  
Your path is yours to choose.

---

## 🌟 Overview

**EasyKarma** introduces a moral alignment system to Minecraft.  
Every player action — helping, killing, their **karma value**.  
Good deeds bring blessings, while evil acts attract chaos and lightning.

---

## ⚙️ Features

- 🧭 **Moral System** — Every mob or player interaction changes karma  
- ⚔️ **Dynamic Effects** — Negative karma attracts lightning and hostile mobs  
- 🌿 **Positive Perks** — Damage bonuses and a chance to survive fatal blows
- 🪶 **Lightweight** — Optimized for survival servers and RP worlds  
- 💬 **Fully Customizable** — All values, effects, and messages editable via `config.yml`  
- 🔐 **Permission-based Commands** — Admin control over karma values  
- 🔄 **Live Reload** — Update your config without restarting the server  

---

## 🧱 Commands

| Command | Description | Permission |
|----------|--------------|-------------|
| `/karma` | View your current karma |  |
| `/karma info [player]` | View another player’s karma | `karma.admin` |
| `/karma give <player> <amount>` | Give karma to a player | `karma.admin` |
| `/karma take <player> <amount>` | Remove karma from a player | `karma.admin` |
| `/karma set <player> <amount>` | Set player’s karma value | `karma.admin` |
| `/karma reload` | Reload configuration | `karma.admin` |

---

## 🔮 Karma Effects

| Type | Trigger | Effect |
|------|----------|--------|
| 🕊️ **Positive Karma** | Helping, trading, killing hostile mobs | +0.2% damage per karma point and 15% death-save chance |
| ⚡ **Negative Karma** | Killing villagers, animals, or players | Lightning chance per tick and higher mob aggression |
| 🌀 **Thresholds** | Configurable in `start-effects` | Decide when effects begin to apply |

---

## 📜 Example Configuration

```yaml
positive-effects:
  damage-bonus-per-point: 0.002
  save-chance: 0.15

negative-effects:
  lightning-chance: 0.001
  mob-aggro-chance: 0.3

start-effects:
  lightning: -10
  mob-aggro-multiplier: -5
