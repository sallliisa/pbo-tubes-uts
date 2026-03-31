public interface Signable {
    void sign(String signer);

    boolean isSigned();
}
