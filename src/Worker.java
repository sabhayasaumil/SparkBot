
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        else if(function.equals("!gif") && func.length > 1)
            return this.gif(event,func[1]);
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
    public MessageBuilder gif(UserChatEvent event,String query)
    {
                MessageBuilder mb = new MessageBuilder();
                    
                try
                {
                    URL url = new URL("http://giphy.com/search/"+query.trim().replaceAll("[ ]+","+"));
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
                    String line;

                    while((line = rd.readLine()) != null) {
                      response.append(line);
                      response.append('\r');
                    }
                    
                    rd.close();

                    String[] part1 = response.toString().split("<div class=\"hoverable-gif\">");
                        if(part1.length == 1)
                        {
                            return null;
                        }
                    
                    String urlnear[] = part1[1].split("data-id=\"");
                    String urle[] = urlnear[1].split("\"");
                    mb.addString("http://media.giphy.com/media/"+urle[0]+"/giphy.gif");
                    
                    return mb;
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            return null;
    }
    

}
