package com.Server;

import com.BotModule.BotCommand;
import com.BotModule.BotFries;
import com.Library.LibraryIO;
import static com.Library.LibraryUtil.log;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

public class MainChat {
	static String TOKEN;
	static IDiscordClient mClient;
	static BotFries Penut = new BotFries();
	static String ID;
	
	public static void main(String[] args) throws DiscordException, RateLimitException {
		TOKEN = LibraryIO.readFile("Data\\Token.dat");
		log("[Server] Logging bot in...\n");
		mClient = new ClientBuilder().withToken(TOKEN).build();
		mClient.getDispatcher().registerListener(new MainChat());
		mClient.login();
		ID = "<@!" + mClient.getApplicationClientID() + ">";
		log("[Server] Bot ID: " + ID + "\n");
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		log("[Server] Bot is now ready!\n");
	}

	@SuppressWarnings("deprecation")
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event)  {
		IMessage message = event.getMessage();
		IUser user = message.getAuthor();
		// log("[Server] UserName: " + user.getName() + "; UserAvatar: " + user.getAvatar() + "; UserDisplayName: " + user.getDisplayName(event.getGuild()) + "\n");
		String msg = message.getContent();
		
		if (user.isBot()) return ;
		IChannel channel = message.getChannel();
		if (cmdCheck(msg, channel)) return ;
		if (!msg.startsWith(ID)) return ;
		
		for (String s: Penut.sendMessage(msg.substring(ID.length())))
			channel.sendMessage("<@!" + user.getStringID() + "> " + s);
	}
	
	private boolean cmdCheck(String msg, IChannel ch) {
		if (BotCommand.isCmd(msg)) {
			boolean isLegalCmd = BotCommand.doCmd(msg);
			if (!isLegalCmd)
				ch.sendMessage("Not a legal command.");
			return true;
		}
		
		return false;
	}
}