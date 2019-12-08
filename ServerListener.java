import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServerListener implements Runnable
{
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private static ArrayList<ObjectOutputStream> outs=new ArrayList<>();
    private static ArrayList<String> names=new ArrayList<>();
    public ServerListener(ObjectInputStream is, ObjectOutputStream os)
    {
            this.is=is;
            this.os=os;
            outs.add(this.os);
    }
    public void run()
    {
        try{
            while(true)
            {
                ToServer c= (ToServer)is.readObject();
                if(c.getCommand()==ToServer.MESSAGE)
                {
                    for(ObjectOutputStream out: outs)
                    {
                        out.writeObject(new FromServer(FromServer.SEND_MESSAGE, c.getCommandData(),c.getSender()));
                        out.reset();
                    }
                }
                else if(c.getCommand()==ToServer.REMOVE_USER)
                {
                    names.remove(c.getSender());
                    for(ObjectOutputStream out: outs)
                    {
                        out.writeObject(new FromServer(FromServer.REMOVE_USER, names,c.getSender()));
                        out.reset();
                    }
                    outs.remove(os);
                    break;
                }
                else if(c.getCommand()==ToServer.ADD_USER)
                {
                    names.add(c.getSender());
                    for(ObjectOutputStream out: outs)
                    {
                        out.writeObject(new FromServer(FromServer.ADD_USER, names, c.getSender()));
                        out.reset();
                    }

                    /*
                    for(ObjectOutputStream out: outs)
                    {
                        out.writeObject(new FromServer(FromServer.ADD_USER, c.getSender()));
                        out.reset();
                    }*/
                }
            }
        }catch(Exception e)
        {
            System.out.println("Error in server listener");
            e.printStackTrace();
        }

    }
}
