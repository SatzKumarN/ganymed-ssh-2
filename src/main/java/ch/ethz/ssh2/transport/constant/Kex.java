
package ch.ethz.ssh2.transport.constant;

public enum Kex {

    DH_GEX_SHA1("diffie-hellman-group-exchange-sha1"), DH_G1_SHA1("diffie-hellman-group1-sha1"),
    DH_G14_SHA1("diffie-hellman-group14-sha1"), DH_G1_SHA2("diffie-hellman-group1-sha256"),
    DH_G14_SHA2("diffie-hellman-group14-sha256");

    private String algorithm;

    public String getAlgorithm() {
	return this.algorithm;
    }

    Kex(String algorithm) {
	this.algorithm = algorithm;
    }
}
