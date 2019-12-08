import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain
{
    public static void main(String[] args)
    {
        Scanner k= new Scanner(System.in);
        //new ChatFrame();
        try{
            Socket connectionToServer = new Socket("127.0.0.1", 8001);

            ObjectInputStream is = new ObjectInputStream(connectionToServer.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(connectionToServer.getOutputStream());
            System.out.println("What's your name? ");
            String s=k.nextLine();
            ClientListener cl= new ClientListener(new ChatFrame(os,s),is);

            os.writeObject(new ToServer(ToServer.ADD_USER, s));
            os.reset();
            Thread t= new Thread(cl);
            t.start();


        }catch(Exception e)
        {
            System.out.println("Error in Client main");
            e.printStackTrace();
        }
    }
}
