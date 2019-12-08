import java.io.Serializable;

public class FromServer implements Serializable
{
    public static final int SEND_MESSAGE= 1;
    public static final int REMOVE_USER= 2;
    public static final int ADD_USER=3;
    //public static final int
    private int command;
    private Object commandData;
    private String sender;
    public FromServer(int command, Object commandData, String sender)
    {
        this.command=command;
        this.commandData=commandData;
        this.sender=sender;
    }
    public FromServer(int command, String sender)
    {
        this.command=command;
        this.sender=sender;
    }
    public int getCommand()
    {return command;}
    public Object getCommandData()
    {return commandData;}
    public String getSender()
    {return sender;}
}
