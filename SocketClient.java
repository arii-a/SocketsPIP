public class SocketClient extends Thread implements OnMessageListener {
    private final Socket socket;
    private final String ip;
    private final DataOutputStream dout;
    private final BufferedReader br;


    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        dout = new DataOutputStream(socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        MediadorCliente.addListenerCliente(this);
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = br.readLine()) != null) {
                System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public synchronized void send(byte[] buffer) {
        try {
            dout.write(buffer);
            dout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(String msg) {
        try {
            dout.write((msg + System.lineSeparator()).getBytes());
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        SocketClient socketClient = new SocketClient(new Socket("localhost", 1825));
        socketClient.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Escriba un mensaje: ");
            socketClient.send((br.readLine()+System.lineSeparator()).getBytes());
        }
    }

    public void closeConnection() {
        try {
            if (br != null) {
                br.close();
            }
            if (dout != null) {
                dout.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Conexión cerrada con " + ip);
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {
    }

    @Override
    public void onMessageCliente(String message) {
        try {
            System.out.println("Mensaje recibido");
            dout.write((message + System.lineSeparator()).getBytes());
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onMessage(Comando c) {
    }


}