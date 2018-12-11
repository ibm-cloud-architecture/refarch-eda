package it;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class HealthEndpointIT {

    private String port = System.getProperty("liberty.test.port");
    private String warContext = System.getProperty("war.context");
    private String endpoint = "/health";
    private String url = "http://localhost:" + port + "/" + warContext + endpoint;

    @Test
    public void testEndpoint() throws Exception {
        System.out.println("Testing endpoint " + url);
        int maxCount = 30;
        int responseCode = makeRequest();
        for(int i = 0; (responseCode != 200) && (i < maxCount); i++) {
          System.out.println("Response code : " + responseCode + ", retrying ... (" + i + " of " + maxCount + ")");
          Thread.sleep(5000);
          responseCode = makeRequest();
        }
        assertTrue("Incorrect response code: " + responseCode, responseCode == 200);
    }

    private int makeRequest() {
      Client client = ClientBuilder.newClient();
      Invocation.Builder invoBuild = client.target(url).request();
      Response response = invoBuild.get();
      int responseCode = response.getStatus();
      response.close();
      return responseCode;
    }
}