import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ClientListener implements Runnable
{
    private ObjectInputStream is;
    private ChatFrame frame;
    private static ArrayList<String> names=new ArrayList<>();
    //private String name;
    public ClientListener(ChatFrame frame, ObjectInputStream is)
    {
        this.frame=frame;
        this.is=is;
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                frame.exit();
                /*if (JOptionPane.showConfirmDialog(frame, "Are you sure to close this window?", "Really Closing?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }*/
            }
        });
    }
    public void run()
    {
        try
        {
            while(true) {
                FromServer c = (FromServer) is.readObject();
                if (c.getCommand() == FromServer.SEND_MESSAGE) {
                    frame.writeText(c.getSender(), (String) c.getCommandData());
                }
                else if(c.getCommand()==FromServer.REMOVE_USER )
                {
                    System.out.println("Remove from user");
                    frame.removeUser((ArrayList<String>)c.getCommandData());
                    frame.bye(c.getSender());
                }
                else if(c.getCommand()==FromServer.ADD_USER)
                {
                    frame.addUser((ArrayList<String>)c.getCommandData());
                }
            }

        }
        catch(Exception e)
        {
            System.out.println("Error in Client listener");
            e.printStackTrace();
        }
        System.out.println("nanni");
    }
}
