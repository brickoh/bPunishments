## Updates 
Most if not all updates will be logged here.

### Update #1 - 31/02/2022 | 01/02/2022
+ TempBanCommand
+ Punishment Durations
+ Made lang more configurable removed the {punishment} field.
+ HistoryCommand (/history [player])
+ NameUtils (Get the players name from just he UUID instead of casting player, also allows us to check if the UUID exists.)
+ History Menus and Sub Menus for each punishment
+ Fixed Punishments creating 4 times.

### Update #2 - 01/02/2022 | 02/02/2022
+ Duration, RemovedAt and AddedAt are now being formatted to give an actual date other than the 1970s.
+ Menus updated
+ Command Framework added
+ Ban, TempBan and UnBan are finished only needs to be pushed through proxy to the other servers (through redis)
+ The expiration time on temp ban now actually gives the correct time, no more 1 minute == 52 years

##Update #3 - 08/02/2022
+ BlacklistCommand and UnBlacklistCommand added
+ WarnCommand and UnWarnCommand added
+ MuteCommand and UnMuteCommand added
+ KickCommand added
+ RedisManager added