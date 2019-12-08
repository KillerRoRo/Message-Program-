import javax.swing.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ChatFrame extends JFrame
{
    private ObjectOutputStream os;
    private JButton btn_exit 		= new JButton("Exit");
    private JButton btn_send 		= new JButton("Send");

    private JLabel lbl_users		= new JLabel("Users:");
    private JList list_users		= new JList();

    private JLabel lbl_chatBox		= new JLabel("Messages:");
    private JScrollPane	scr_chatBox	= null;
    private JTextArea txt_chatBox	= new JTextArea();

    private JLabel lbl_message		= new JLabel("Enter Message:");
    private JTextArea txt_message	= new JTextArea();


    private String userName			= "";
    //private static ArrayList<String> users = new ArrayList<String>();

    public ChatFrame(ObjectOutputStream os, String userName)
    {
        super("Chat Client");
        this.userName = userName;
        this.os=os;
        /*try{
            os.writeObject(new ToServer(ToServer.ADD_USER, userName));
            os.reset();
        }catch(Exception e) {
            System.out.println("Error in chat frame");
            e.printStackTrace();
        }*/

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(800,800);
        setResizable(false);
        //users.add(userName);

       // list_users.setListData(users.toArray());
        list_users.setEnabled(false);
        lbl_users.setBounds(640,30,130,20);
        list_users.setBounds(640,50,130,550);


        scr_chatBox = new JScrollPane(txt_chatBox,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scr_chatBox.setBounds(20,50,600,550);
        lbl_chatBox.setBounds(20,30,100,20);
        txt_chatBox.setEditable(false);

        txt_message.setBounds(20,650,600,80);
        lbl_message.setBounds(20,630,100,20);

        btn_send.setBounds(640,650,130,30);
        btn_exit.setBounds(640,700,130,30);

        setLayout(null);
        add(txt_message);
        add(lbl_message);
        add(lbl_users);
        add(lbl_chatBox);
        add(scr_chatBox);
        add(list_users);
        add(btn_send);
        add(btn_exit);

        btn_exit.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        exit();
                    }
                }
        );

        btn_send.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        sendtxt_messageButton();
                    }
                }
        );

        setVisible(true);
    }

    public void exit()
    {
        //System.exit(0);
        try{
            System.out.println(userName+ " says to remove");
            os.writeObject(new ToServer(ToServer.REMOVE_USER, userName));
            os.reset();
        }catch(Exception e)
        {
            System.out.println("Error in chat frame");
            e.printStackTrace();
        }
    }

    public void sendtxt_messageButton()
    {
        if(txt_message.getText().isEmpty())
        {

        }
        else
        {
            try{
                os.writeObject(new ToServer(ToServer.MESSAGE, txt_message.getText(), userName));
                os.reset();
                txt_message.setText("");
            }catch(Exception e) {
                System.out.println("Error in frame");
                e.printStackTrace();
            }
        }

        /*String m = userName + ": " + txt_message.getText();
        txt_chatBox.append(m+"\n");*/
    }
    public void writeText(String username, String txt)
    {
        txt_chatBox.setText(txt_chatBox.getText() +username+": " + txt + "\n");
    }
    public void addUser(ArrayList<String> users)
    {
        //users.add(n);
        System.out.println("Users: ");
        for(String user: users)
        {
            System.out.print(user+ ", ");
        }

        list_users.setListData(users.toArray());


    }
    public void removeUser(ArrayList<String> users)
    {
        System.out.println("someone was removed");
        list_users.setListData(users.toArray());
    }
    public boolean bye(String name)
    {
        if(name.equals(userName))
        {
            System.exit(0);
            return true;
        }
            return false;
    }



}
