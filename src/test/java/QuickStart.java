import io.muserver.Method;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.acme.AcmeCertManager;
import io.muserver.acme.AcmeCertManagerBuilder;
import io.muserver.handlers.HttpsRedirectorBuilder;

import java.util.concurrent.TimeUnit;

public class QuickStart {

    public static void main(String[] args) throws Exception {
        boolean isLocal = false; // figure out from env somehow

        AcmeCertManager certManager = AcmeCertManagerBuilder.letsEncryptStaging()
            .withDomain("your-domain.example.org")
            .withConfigDir("target/ssl-config")
            .disable(isLocal)
            .build();

        MuServer server = MuServerBuilder.muServer()
            .withHttpPort(80)
            .withHttpsPort(443)
            .withHttpsConfig(certManager.createHttpsConfig())
            .addHandler(certManager.createHandler())
            .addHandler(isLocal ? null :
                HttpsRedirectorBuilder.toHttpsPort(443)
                    .withHSTSExpireTime(1, TimeUnit.DAYS)
                    .includeSubDomains(true)
            )
            .addHandler(Method.GET, "/", (req, resp, path) -> {
                resp.write("Hello, world");
            })
            .start();

        certManager.start(server);
        System.out.println("Started server at " + server.uri());

    }

}
