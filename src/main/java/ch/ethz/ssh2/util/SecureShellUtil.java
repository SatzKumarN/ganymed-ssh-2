package ch.ethz.ssh2.util;

import ch.ethz.ssh2.transport.constant.Kex;

public class SecureShellUtil {

    private SecureShellUtil() {
    }

    public static String getShaType(String kexAlgorithm) {
	String shaType = "SHA1";
	if (kexAlgorithm.equals(Kex.DH_G1_SHA2.getAlgorithm()) || kexAlgorithm.equals(Kex.DH_G14_SHA2.getAlgorithm())) {
	    shaType = "SHA2";
	}
	return shaType;
    }

}
