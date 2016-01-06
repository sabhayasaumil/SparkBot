
import java.text.*;
import java.util.*;
import me.itsghost.jdiscord.events.UserChatEvent;
import me.itsghost.jdiscord.message.Message;
import me.itsghost.jdiscord.message.MessageBuilder;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.GroupUser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Saumil
 */
public class Worker {
    
    String version = "1.0.1";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    
    public Worker()
    {
    }
    
    public MessageBuilder Process(String query, UserChatEvent event)
    {
        
        String[] func = query.split(" ",2);
        String function = func[0].toLowerCase();
        
        if(function.equals("!raffle"))
            return this.Raffle(event);
        else if(function.equals("!version"))
            return this.Version(event);
        else if(function.equals("!update"))
            return this.Update(event);
        return null;
    
    }
    public MessageBuilder Raffle(UserChatEvent event)
    {
        
            if(!isAdmin(event))
            {
                    MessageBuilder mb = new MessageBuilder();
                    mb.addUserTag(event.getUser(), event.getGroup());
                    mb.addString(" Yo only admin could do Raffle.");
            
            }
            Group group = event.getGroup();
            List<GroupUser> GU = event.getServer().getConnectedClients();
            ArrayList<GroupUser> win = new ArrayList<GroupUser>();
            for(GroupUser gu:GU)
            {
                //System.out.println(gu.getUser().getUsername()+" status " + gu.getUser().getOnlineStatus());
                if(gu.getUser().getOnlineStatus() != null)
                {
                    if(!gu.getUser().getUsername().toLowerCase().contains("bot"))
                        win.add(gu);
                }

            }
            Collections.shuffle(win);

            MessageBuilder mb = new MessageBuilder();
            mb.addUserTag(win.get(0).getUser(), event.getGroup());
            mb.addString(": is the winner for this raffle");
            return mb;
    }
    public MessageBuilder Version(UserChatEvent event)
    {
                    MessageBuilder mb = new MessageBuilder();
                    mb.addUserTag(event.getUser(), event.getGroup());
                    mb.addString(" Y0. Version is " + version + " Running from " + dateFormat.format(date));
                    return mb;
    }
    public boolean isAdmin(UserChatEvent event)
    {
        String user = event.getUser().getUser().getUsername();
        return user.toLowerCase().equals("falcon") || user.toLowerCase().equals("samthehawk");
    }
    public MessageBuilder Update(UserChatEvent event)
    {
                    MessageBuilder mb = new MessageBuilder();
                    mb.addUserTag(event.getUser(), event.getGroup());
                    mb.addString(" Yo, hey franz Deployment Successful.");
                    return mb;
    }

}
