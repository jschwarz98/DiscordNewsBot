# DiscordNewsBot

A simple Discord-Bot to read the RSS-Feed of different News-Sites, for example [Spiegel-News](https://www.spiegel.de/).
Using JDA and Gradle, this project updates every minute and tries to send the news to every member of the specified
server.

## Usage

You can start the RSS-Feed scraping by using the following commands in the designated News-Textchat.

``` discord
!start
```

starts the rss request cycle.

``` discord
!stop
```

stops the rss request cycle.

## Configuration options

This application is dependent on a .env file in the root with mandatory and optional parameters.

### Mandatory Keys

| key             | description                                                                      |
|-----------------|----------------------------------------------------------------------------------|
| TOKEN           | The token provided by Discord. Used to connect to the API.                       |
| NEWS_CHANNEL_ID | The channel in which you want the bot to post the news scraped from the RSS-Feed |

### Optional Keys

| key                     | default value | description                                                                                                                                                   |
|-------------------------|---------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| POLLING_RATE_IN_SECONDS | 60            | Must be a whole number representing the delay between request cycles. Default is a 60 second delay                                                            |
| DATABASE_PATH           | ./uuids.db    | Where the uuid's of the retrieved items will be stored. Used to avoid sending duplicate Articles between runs as the RSS-Feed may contain already sent items. |

## Examples

![Example1](https://user-images.githubusercontent.com/44731247/217674958-4b29629f-e8c8-4fff-83ca-8aea5f734da8.png)

## Future plans

* Adding functionality to change the request timing from the discord-chat.
* Adding ability to work in different chats or in private user chats.