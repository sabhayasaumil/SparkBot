/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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
    Worker worker = new Worker();
    
 //2014/08/06 15:59:48
    
    public DiscordBot()
    {
        api = new DiscordBuilder().build();
        api.getEventManager().registerListener(this);
        
    }
    
    public void raffle(UserChatEvent event)
    {
        String message = event.getMsg().getMessage();
        String user = event.getUser().getUser().getUsername();
        
        if(message.charAt(0) != '!')
            return;
        MessageBuilder mb = worker.Process(message, event);
        if(mb == null)
            return;
        Message reply = mb.build(api);
        event.getGroup().sendMessage(reply);
        
    }
    
     public void connect(String username,String password)
    {
        try 
        {
            api.login(username,password);

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
        BufferedReader br = null;
        try {
                    DiscordBot myBot = new DiscordBot();
                    br = new BufferedReader(new FileReader("/var/www/bot/authenticate.txt"));
                    String username = br.readLine();
                    String password = br.readLine();
                    myBot.connect(username,password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
}
