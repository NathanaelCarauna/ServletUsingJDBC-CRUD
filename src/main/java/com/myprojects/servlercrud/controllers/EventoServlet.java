package com.myprojects.servlercrud.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/eventos")
public class EventoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Charset", "UTF-8");
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/servletjdbc?user=postgres&password=123456&ssl=false";
			Connection conn = DriverManager.getConnection(url);

			Statement listarEventos = conn.createStatement();
			String idp = req.getParameter("id");
			String sql;
			if(idp != null) {
				sql = "SELECT * from evento WHERE eventoId = " + idp;
			}else {
				sql = "SELECT * from evento ORDER BY eventoID";				
			}
				
			ResultSet result = listarEventos.executeQuery(sql);

			while (result.next()) {
				long id = result.getLong("eventoid");
				String descricao = result.getString("descricao");
				String emocoes = result.getString("emocoes");
				resp.getOutputStream().println("[\nID: " + id + ";\nDescricao: " + descricao + "\nEmocoes: " + emocoes + "\n]");
			}

			conn.close();
			listarEventos.close();
			result.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/servletjdbc?user=postgres&password=123456&ssl=false";
			Connection conn = DriverManager.getConnection(url);			
			Statement createEvent = conn.createStatement();
			
			String id = req.getParameter("id");
			String descricao = req.getParameter("descricao");
			String emocoes = req.getParameter("emocoes");

			String sql = String.format("INSERT INTO evento VALUES (%s,'%s', '%s')", id, descricao, emocoes);
			System.out.println(sql);

			createEvent.executeUpdate(sql);

			conn.close();
			createEvent.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/servletjdbc?user=postgres&password=123456&ssl=false";
			Connection conn = DriverManager.getConnection(url);			
			Statement createEvent = conn.createStatement();
			
			String id = req.getParameter("id");

			String sql = String.format("DELETE FROM evento WHERE eventoid = %s", id);
			System.out.println(sql);

			createEvent.executeUpdate(sql);

			conn.close();
			createEvent.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost/servletjdbc?user=postgres&password=123456&ssl=false";
			Connection conn = DriverManager.getConnection(url);			
			Statement createEvent = conn.createStatement();
			
			String id = req.getParameter("id");
			String descricao = req.getParameter("descricao");
			String emocoes = req.getParameter("emocoes");
			String sql = "";
			if(descricao == null) {
				sql = String.format("UPDATE evento SET emocoes = '%s' WHERE eventoid = %s", emocoes, id );				
			}
			if(emocoes == null) {
				sql = String.format("UPDATE evento SET descricao = '%s' WHERE eventoid = %s", descricao, id);								
			}
			if(descricao != null && emocoes != null) {
				sql = String.format("UPDATE evento SET descricao = '%s', emocoes = '%s' WHERE eventoid = %s" , descricao, emocoes, id);								
			}
			System.out.println(sql);

			createEvent.executeUpdate(sql);

			conn.close();
			createEvent.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			resp.getWriter().write(e.getMessage());
		}
	}
}
