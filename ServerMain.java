import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain
{

    //private ArrayList<String> messages= new ArrayList<>();
    public static void main(String[]args)
    {
        try{
            ServerSocket serverSocket= new ServerSocket(8001);
            while(true)
            {
                Socket socket= serverSocket.accept();
                ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream is= new ObjectInputStream(socket.getInputStream());
                ServerListener sl= new ServerListener(is, os);
                Thread t= new Thread(sl);
                t.start();
            }

        }catch(Exception e) {
            System.out.println("Error in server main: ");
            e.printStackTrace();
        }

    }
}
