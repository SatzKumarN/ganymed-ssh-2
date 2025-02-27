/*
 * Copyright (c) 2006-2011 Christian Plattner. All rights reserved.
 * Please refer to the LICENSE.txt for licensing details.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.HTTPProxyData;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class BasicWithHTTPProxy
{
	public static void main(String[] args)
	{
		String hostname = "my-ssh-server";
		String username = "joe";
		String password = "joespass";

		String proxyHost = "192.168.1.1";
		int proxyPort = 3128; // default port used by squid

		try
		{
			/* Create a connection instance  with HTTP proxy*/
			Connection conn = new Connection(hostname, 22, new HTTPProxyData(proxyHost, proxyPort));

			// if the proxy requires basic authentication:
			// conn.setProxyData(new HTTPProxyData(proxyHost, proxyPort, "username", "secret"));

			/* Now connect (through the proxy) */

			conn.connect();

			/* Authenticate.
			 * If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand("uname -a && date && uptime && who");

			System.out.println("Here is some information about the remote host:");

			/* 
			 * This basic example does not handle stderr, which is sometimes dangerous
			 * (please read the FAQ).
			 */

			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

			while (true)
			{
				String line = br.readLine();
				if (line == null)
				{
					br.close();
					break;
				}
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */

			System.out.println("ExitCode: " + sess.getExitStatus());

			/* Close this session */

			sess.close();

			/* Close the connection */

			conn.close();

		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
