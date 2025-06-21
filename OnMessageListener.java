public interface OnMessageListener {
    void onMessage(String message);
    void onMessage(Comando c);
    void onMessageCliente(String message);
    void onClose();
}