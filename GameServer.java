/*
Usage:
java GameServer <max num of players>
*/

public class GameServer {
   public static void main (String [] args) throws Exception {
		if (args.length != 1){
			System.out.println("\nUsage: sudo java GameServer <max num of players>");
			System.exit(1);
		}

		TCPServer myServer = new TCPServer(); //start chat server
		myServer.start();

		//args[0] is the max num of players
		UDPServer myUDPServer = new UDPServer(Integer.parseInt(args[0]));
	}
}
