/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package discordbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.DiscordBuilder;
import me.itsghost.jdiscord.event.EventListener;
import me.itsghost.jdiscord.events.UserChatEvent;
import me.itsghost.jdiscord.exception.BadUsernamePasswordException;
import me.itsghost.jdiscord.exception.DiscordFailedToConnectException;
import me.itsghost.jdiscord.message.Message;
import me.itsghost.jdiscord.message.MessageBuilder;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.GroupUser;

/**
 *
 * @author Saumil
 */
public class DiscordBot implements EventListener{
    
    private DiscordAPI api;
    public DiscordBot()
    {
        api = new DiscordBuilder().build();
        api.getEventManager().registerListener(this);
    }
    
    public void waffle(UserChatEvent event)
    {
        String message = event.getMsg().getMessage();
        String user = event.getUser().getUser().getUsername();
        
        if(message.toLowerCase().equals("!waffle"))
        if(user.toLowerCase().equals("falcon") || user.toLowerCase().equals("samthehawk"))
        {        
            
            {
                Group group = event.getGroup();
                List<GroupUser> GU = event.getServer().getConnectedClients();
                ArrayList<GroupUser> win = new ArrayList<GroupUser>();
                for(GroupUser gu:GU)
                {
                    System.out.println(gu.getUser().getUsername()+" status " + gu.getUser().getOnlineStatus());
                    if(gu.getUser().getOnlineStatus() != null)
                    {
                        if(!gu.getUser().getUsername().toLowerCase().contains("bot"))
                            win.add(gu);
                    }

                }
                Collections.shuffle(win);
                
                MessageBuilder mb = new MessageBuilder();
                mb.addUserTag(win.get(0).getUser(), event.getGroup());
                mb.addString(": is the winner for this waffle");
                Message reply = mb.build(api);
                event.getGroup().sendMessage(reply);
                
            }
        }
        else
        {
                MessageBuilder mb = new MessageBuilder();
                mb.addUserTag(event.getUser(), event.getGroup());
                mb.addString(" Yo only admin could do Waffle.");
                Message reply = mb.build(api);
                event.getGroup().sendMessage(reply);
        
        }
    }
    
    
    
    public void connect()
    {
        try 
        {
            api.login("bobbyleone91@gmail.com","Bobby_1991");
            /*List<GroupUser> GU = api.getServerById("133983214636105728").getConnectedClients();
            for(GroupUser gu:GU)
            {
                System.out.println(gu.getUser().getUsername().toString());
            }
            */
            
        }
        catch (BadUsernamePasswordException ex) 
        {
            ex.printStackTrace();
        }
        catch (DiscordFailedToConnectException ex) 
        {
            ex.printStackTrace();
        }
            
            
        
    }

    public static void main(String[] args) 
    {
        DiscordBot myBot = new DiscordBot();
        myBot.connect();
        
    }
    
}
