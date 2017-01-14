# Contributing Guide

Hello there, thanks for your interest in conteributing to VoxelGamesLib. I apprechiate any help, but I would like you to read these short guidelines first, so that we all can save time.

## For everyone

Anyone can help, you don't need to be a developer. If you don't know how to write code, you can still contribute.

### Translations

All messages that will reach players can be translated into every language you desire.  
I don't speak many languages (only english and german) so I need your help for that. 
If you translated the messages for your servers, submit your translation as a Pull Request (or if you don't know how to do that, just create a new issue) so I (with the help of others and gtranslate) can validate the translations and add them as default so everyone can use them.  
You can use {colors} in the messages and other variables. You can see which variables are available for each key [here](/api/src/main/java/me/minidigger/voxelgameslib/api/lang/LangKey.java#L12)
You can fine the most up to date lang file [here](/testserver/plugins/VoxelGamesLibBukkit/lang/en.properties)  

In the future I plan to setup a service that makes translation very easy, like crowdin for example, but I will wait for that till there is actual demand.

### Feature/Gamemode Requests

You have a new idea for a new gamemode? You saw a gamemode on a server that you want on your server too? Just create a new issue where you explain the gamemode. if it is an existing gamemode, vidoes and a link to the origional server can help me too.  

If you want to create a new gamemode using the json format but you need a new feature for that? Create a issue and explain what it should do and what you need it for.

## For Developers

### Building

You can build this project very easily, as it uses gralde. 

Just clone it using `git clone https://github.com/MiniDigger/VoxelGamesLib.git` and then build it using `gradle build`.  
The finished aritfacts will end up in the sub projects, so the bukkit plugin jar will be located in `/bukkit/build/libs`

### Contributing code

There are many parts of the project you can contribute too. You can find a list below.  
For every PR there is a small set of rules tho:
* Code Style: Use the formatting style located in the root of the project. (https://github.com/MiniDigger/VoxelGamesLib/blob/master/VoxelGamesLib.xml) Its a modified version of the google java code style.
* JavaDocs: Every public method needs to have javadocs. 
* Comments: Please try to throw in some comments into your code. You don't need to comment every single line, but you should try to structurize and explain your algorithems using comments
* The PR: Explain your changes and why you did them in the PR please.
* Communitcate: If a PR does not receive any activity after a month, I will most likley close it. If you know that you will not be able to work in the PR in the future, please state so in a comment so that I can reshedule the PR.

#### New Gamemodes

todo

#### New Features

todo

#### New Servermod

todo

#### Other stuff

todo
