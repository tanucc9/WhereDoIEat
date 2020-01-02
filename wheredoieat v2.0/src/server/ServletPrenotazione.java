package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AttivitaBean;
import bean.PersonaBean;
import bean.PrenotazioneBean;
import dao.AttivitaDAO;
import dao.PrenotazioneDAO;

/**
 * Servlet implementation class ServletPrenotazione
 */
@WebServlet("/ServletPrenotazione")
public class ServletPrenotazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPrenotazione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data=request.getParameter("data");
		String oraString=request.getParameter("ora");
		int ora=Integer.parseInt(oraString);
		String postiString=request.getParameter("posti");
		int posti=Integer.parseInt(postiString);
		String idString=request.getParameter("id");
		int id=Integer.parseInt(idString);
		String annoString=data.substring(0, 4);
		String meseString=data.substring(5, 7);
		String giornoString=data.substring(8, 10);
		int anno =Integer.parseInt(annoString);
		int mese=Integer.parseInt(meseString);
		int giorno=Integer.parseInt(giornoString);
		PersonaBean utente=new PersonaBean();
		utente=(PersonaBean) request.getSession().getAttribute("cliente");
		int t = 0; //numero giorni dall inizio dell'anno
		if(mese==1) {
			t=giorno;
		}
		if(mese==2) {
			t=31+giorno;
		}
		if(mese==3) {
			t=31+28+giorno;
		}
		if(mese==4) {
			t=31+28+31+giorno;
		}
		if(mese==5) {
			t=31+28+31+30+giorno;
		}
		if(mese==6) {
			t=31+28+31+30+31+giorno;
		}
		if(mese==7) {
			t=31+28+31+30+31+30+giorno;
		}
		if(mese==8) {
			t=31+28+31+30+31+30+31+giorno;
		}
		if(mese==9) {
			t=31+28+31+30+31+30+31+31+giorno;
		}
		if(mese==10) {
			t=31+28+31+30+31+30+31+31+30+giorno;
		}
		if(mese==11) {
			t=31+28+31+30+31+30+31+31+30+31+giorno;
		}
		if(mese==12) {
			t=31+28+31+30+31+30+31+31+30+31+30+giorno;
		}
		if((mese>2)&&(anno%4==0)) {
			t=t+1;
		}
		
		int val=anno+(anno-1)/4+(anno-1)/100+(anno-1)/400+t;
		String day = "";
		if(val%7==0) {
			day="Lunedi";
		}
		if(val%7==1) {
			day="Martedi";
		}
		if(val%7==2) {
			day="Mercoledi";
		}
		if(val%7==3) {
			day="Giovedi";
		}
		if(val%7==4) {
			day="Venerdi";
		}
		if(val%7==5) {
			day="Sabato";
		}
		if(val%7==6) {
			day="Domenica";
		}
		AttivitaDAO adao=new AttivitaDAO();
		AttivitaBean a=new AttivitaBean();
		a=adao.doRetrieveByKey(id);
		if(day.equals(a.getGiornoChiusura())) {
			request.setAttribute("giornoChiusura", true);
			request.setAttribute("singolaAttivita", a);
			RequestDispatcher rd=request.getRequestDispatcher("attivitaSpecifica.jsp");
			rd.forward(request, response);
		}
		
		PrenotazioneDAO pdao=new PrenotazioneDAO();
		int tot,tot2,totale;
		try {
			response.getWriter().println("1");
			tot=pdao.doRetrieveByOra(ora,id,data);
			response.getWriter().println("2");
			if(ora%100==0) {
				response.getWriter().println("3");
				tot2=pdao.doRetrieveByOra(ora-70,id,data);
			} else {
				response.getWriter().println("4");
				tot2=pdao.doRetrieveByOra(ora-30,id,data);
			}
			response.getWriter().println("5");
			totale=tot+tot2;
			int postiDisp=0;
			postiDisp=a.getNumPosti()-totale;
			if(posti<=postiDisp) {
				PrenotazioneBean p=new PrenotazioneBean();
				p.setAttivitaIDAttivita(id);
				p.setData(data);
				p.setNumPosti(posti);
				p.setOra(ora);
				p.setPersonaUsername(utente.getUsername());
				response.getWriter().println("6");
				pdao.doSave(p);
				response.getWriter().println("7");
				request.setAttribute("ok", true);
				request.setAttribute("singolaAttivita", a);
				RequestDispatcher rd=request.getRequestDispatcher("attivitaSpecifica.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("postiNonDisp", true);
				request.setAttribute("singolaAttivita", a);
				RequestDispatcher rd=request.getRequestDispatcher("attivitaSpecifica.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//response.getWriter().println(day+"   "+val+"  "+val%7);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
