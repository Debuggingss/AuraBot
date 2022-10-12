# AuraBot
Clone of Hypixel's old watchdog aurabot.

This is more of a Proof-of-concept project because aurabots were always useless as every client has an antibot module.

### Showcase
![Showcase](https://i.debuggings.dev/6npVnQS3.gif)  
![Chat Feedback](https://i.debuggings.dev/3XP6fBHy.png)

### Command:
**/aurabot &lt;username&gt; [attack]**

If the attack argument is not empty, the bot will hit the player (dealing 0 damage). Otherwise the bot just swings its arm.

### Customization
The plugin has a configuration file where you can change what words the name generator should pick from.  
Default configuration:
```yaml
name_words:
  - okay
  - hello
  - hi
  - wazzup
  - fam
  - yoho
  - yahoo
  - joe
  - pizza
```

**This plugin doesn't use any 3rd party libraries.**
