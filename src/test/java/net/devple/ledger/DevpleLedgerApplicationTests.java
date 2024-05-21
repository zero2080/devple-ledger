package net.devple.ledger;

import java.util.TimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "doc.ledger.devple.net")
public class DevpleLedgerApplicationTests {


    protected MockMvc mockMvc;
    protected WebApplicationContext context;
    protected MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    public void setContext(WebApplicationContext context) {
        this.context = context;
    }
}
