public class Servidor extends Thread implements OnMessageListener {

    private final ServerSocket serverSocket;

    public Servidor() throws IOException {
        Mediador.addListener(this);
        int port = Integer.parseInt(System.getenv("PORT"));
        this.serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado en puerto: " + port);

    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = this.serverSocket.accept();
                SocketClient client = new SocketClient(socket);
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose() {
        System.out.println("Servidor: Se ha cerrado la conexión");
    }

    @Override
    public void onMessageCliente(String message) {

    }

    @Override
    public void onMessage(Comando c) {
    }

}