package control;

import static org.junit.Assert.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import bean.AttivitaBean;
import bean.PersonaBean;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;

import dao.AttivitaDAO;
import dao.PrenotazioneDAO;
import server.ServletPrenotazione;

public class TestServletPrenotazione {

	@Mock
	HttpServletRequest req;
	@Mock
	HttpServletResponse res;
	@Mock
	HttpSession session;
	@Mock
	RequestDispatcher rd;
	
	ServletPrenotazione servlet;
	
	@Mock
	AttivitaDAO adao=new AttivitaDAO();
	@Mock 
	PrenotazioneDAO pdao=new PrenotazioneDAO();	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		servlet=new ServletPrenotazione();
		servlet.setADao(adao);
		servlet.setPDao(pdao);
		when(req.getSession()).thenReturn(session);
	}
	
	@Test
	public void prenota() throws SQLException, ServletException, IOException {
		when(req.getParameter("data")).thenReturn("2020-03-05");
		when(req.getParameter("ora")).thenReturn("2000");
		when(req.getParameter("posti")).thenReturn("12");
		when(req.getParameter("id")).thenReturn("1");
		PersonaBean p=new PersonaBean("tanucc","Gaetano", "Mauro", "g.mauro14@studenti.unisa.it", "393404181948", "Avellino",1, "Atripalda", "mistersessa");
		when(session.getAttribute("cliente")).thenReturn(p);
		AttivitaBean a=new AttivitaBean(1, "El pampa Sosa", "Fisciano", 13, 23, "Mercoledi", "via bomber", "39340412122", 200, "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3025.9426235718774!2d14.769572615021944!3d40.67523314780173!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x133bc239baaf7187%3A0x9cae3635339ffee0!2sSalerno%20Stazione!5e0!3m2!1sit!2sit!4v1577092641285!5m2!1sit!2sit", "amecuomo");
		when(adao.doRetrieveByKey(Mockito.anyInt())).thenReturn(a);
		when(pdao.doRetrieveByOra(2000, 1, "2020-03-04")).thenReturn(50);
		when(pdao.doRetrieveByOra(2000-70, 1, "2020-03-04")).thenReturn(20);
		when(req.getAttribute("ok")).thenReturn(true);
		boolean ok=(boolean) req.getAttribute("ok");
		when(req.getRequestDispatcher("attivitaSpecifica.jsp")).thenReturn(rd);
		servlet.doGet(req,res);
		verify(rd).forward(req, res);
		assertTrue(ok);
	}
	
	
	@Test
	public void postiNonDisp() throws SQLException, ServletException, IOException {
		when(req.getParameter("data")).thenReturn("2020-03-05");
		when(req.getParameter("ora")).thenReturn("2000");
		when(req.getParameter("posti")).thenReturn("12");
		when(req.getParameter("id")).thenReturn("1");
		PersonaBean p=new PersonaBean("tanucc","Gaetano", "Mauro", "g.mauro14@studenti.unisa.it", "393404181948", "Avellino",1, "Atripalda", "mistersessa");
		when(session.getAttribute("cliente")).thenReturn(p);
		AttivitaBean a=new AttivitaBean(1, "El pampa Sosa", "Fisciano", 13, 23, "Mercoledi", "via bomber", "39340412122", 200, "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3025.9426235718774!2d14.769572615021944!3d40.67523314780173!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x133bc239baaf7187%3A0x9cae3635339ffee0!2sSalerno%20Stazione!5e0!3m2!1sit!2sit!4v1577092641285!5m2!1sit!2sit", "amecuomo");
		when(adao.doRetrieveByKey(Mockito.anyInt())).thenReturn(a);
		when(pdao.doRetrieveByOra(2000, 1, "2020-03-04")).thenReturn(170);
		when(pdao.doRetrieveByOra(2000-70, 1, "2020-03-04")).thenReturn(20);
		when(req.getAttribute("postiNonDisp")).thenReturn(true);
		boolean postiNonDisp=(boolean) req.getAttribute("postiNonDisp");
		when(req.getRequestDispatcher("attivitaSpecifica.jsp")).thenReturn(rd);
		servlet.doGet(req,res);
		verify(rd).forward(req, res);
		assertTrue(postiNonDisp);
	}
	

}
