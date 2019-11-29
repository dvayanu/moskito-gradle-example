package de.nitrobox.service.simple.rest;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nitrobox.service.simple.service.SimpleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SimpleResource.class)
//@SpringBootTest
//@AutoConfigureMockMvc
class SimpleResourceTest {

  private static final String TENANT_HEADER = "X-Nitrobox-Tenant-Id";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
//  @Autowired
  private SimpleService simpleService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void sanityTest() throws Exception {
    // when
    this.mockMvc.perform(
        get("/api/simple")
            .header(TENANT_HEADER, "1"))
        // then
        .andExpect(status().isOk())
        .andExpect(content().string("Hello World!"));
    verify(simpleService).someMethod();
  }
}