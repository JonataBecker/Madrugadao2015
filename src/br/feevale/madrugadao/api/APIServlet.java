package br.feevale.madrugadao.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.feevale.madrugadao.db.Entidade;
import br.feevale.madrugadao.db.Filtro;
import br.feevale.madrugadao.db.FiltroFactory;
import br.feevale.madrugadao.db.SQLCreator;
import br.feevale.madrugadao.db.Tabela;
import br.feevale.madrugadao.db.View;

public class APIServlet extends HttpServlet {

	private static final String VIEW = "view";

	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		try {
			InitialContext initialContext = new InitialContext();
			dataSource = (DataSource) initialContext
					.lookup("java:comp/env/jdbc/DefaultDB");
		} catch (NamingException e) {
			throw new ServletException(e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Filtro filtro = FiltroFactory.create(req.getParameterMap());
			Entidade entidade = createEntidade(req.getRequestURI());
			SQLCreator sqlCreator = new SQLCreator(entidade, filtro);
			resp.getWriter().println(new EntidadeDAO(connection, sqlCreator).busca());
		} catch (Exception e) {
			resp.getWriter().println(e.getMessage());
			resp.setStatus(405);
		} finally {
			try {
				connection.close();
			} catch(SQLException e){}
		}
	}

	private Entidade createEntidade(String url) throws Exception {
		String[] param = url.split("/");
		if (param.length == 0) {
			throw new Exception("Entidade não localizada!");
		}
		String entidade = param[param.length - 1];
		// Se for acesso a view
		if (param.length > 1 && param[param.length - 2].equals(VIEW)) {
			return new View(entidade);
		}
		return new Tabela(entidade);
	}

}
