package com.epam.lab.service;

import com.epam.lab.config.TestRootConfig;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestRootConfig.class})
//@WebAppConfiguration
//@FixMethodOrder
public class UserServiceImplTest {

//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext applicationContext;
//
//    @Before
//    public void setUp() {
//       mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
//    }
//
//    @Test(expected = NoSuchBeanDefinitionException.class)
//    public void testContext() {
//        Assert.assertNotNull(applicationContext.getBean(SecurityService.class));
//    }
//
//    @Test
//    public void testController() throws Exception {
//        mockMvc.perform(get("/")).andDo(print()).andExpect(view().name("login"));
//    }
}