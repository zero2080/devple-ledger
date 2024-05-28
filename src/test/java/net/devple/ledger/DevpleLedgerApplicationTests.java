package net.devple.ledger;

import java.nio.charset.StandardCharsets;
import java.util.TimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "doc.ledger.devple.net")
public class DevpleLedgerApplicationTests {


    protected MockMvc mockMvc;
    protected WebApplicationContext context;

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
            .alwaysDo(MockMvcResultHandlers.print())
            .build();
    }

    @Autowired
    public void setContext(WebApplicationContext context) {
        this.context = context;
    }
}
