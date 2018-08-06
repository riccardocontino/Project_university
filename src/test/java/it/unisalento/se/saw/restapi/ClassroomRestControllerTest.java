package it.unisalento.se.saw.restapi;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.ServletContextLiveBeansView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import it.unisalento.se.saw.Iservices.IClassroomService;
import it.unisalento.se.saw.Iservices.ITeachingService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Report;
import it.unisalento.se.saw.domain.Reportstatus;

@RunWith(MockitoJUnitRunner.class)
public class ClassroomRestControllerTest {
	
	
	
	private MockMvc mockMvc;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(), 
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8")
			);
	@Mock
	private IClassroomService classroomServiceMock;
	
	@Before
	public void setUp() {
		
		mockMvc = MockMvcBuilders.standaloneSetup(new ClassroomRestController(classroomServiceMock))
					.setViewResolvers(viewResolver())
					.build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		Classroom cl1 = new Classroom();
		cl1.setDescription("prova");
		cl1.setName("y1");
		Classroom cl2 = new Classroom();
		cl2.setDescription("prova2");
		cl2.setName("y2");
		
		when(classroomServiceMock.getAll()).thenReturn(Arrays.asList(cl1, cl2));
		
		mockMvc.perform(get("/classroom/getAll"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$[0].name", is("y1")))
		.andExpect(jsonPath("$[0].description", is("prova")))
		.andExpect(jsonPath("$[1].name", is("y2")))
		.andExpect(jsonPath("$[1].description", is("prova2")));
		
		verify(classroomServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(classroomServiceMock);
	}
	/*
	@Test
	public void saveClassroomTest() throws Exception {
		
	}*/
	
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}