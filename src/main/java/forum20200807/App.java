package forum20200807;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class App {

	public static void main(String[] args) throws Exception {
		Connection conexao = null;
		try {
			conexao = abrirConexao();
			truncarTabelaFuncionarios(conexao);
			
			
			tabelaFuncionarios(conexao);

			inserirDoisMilFuncionarios(conexao);
			
			//deletarFuncionario(conexao);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}

	}


	private static void inserirDoisMilFuncionarios(Connection conexao) throws Exception {
		PreparedStatement inserirFuncionario = null;
		try {
			inserirFuncionario = conexao.prepareStatement("insert into funcionarios_de_uma_empresa (id,nome_do_cargo) values (?,?)");
			for (int contador = 1; contador <= 2000; contador++) {
				inserirFuncionario.setLong(1, contador);
				inserirFuncionario.setString(2, "Professor" /*+ contador*/);
				inserirFuncionario.execute();
			}
		} finally {
			inserirFuncionario.close();
		}

	}

	private static void tabelaFuncionarios(Connection conexao) throws Exception {
		Statement criarTabela = null;
		try {
			criarTabela = conexao.createStatement();
			criarTabela.execute("create table if not exists funcionarios_de_uma_empresa ("
					+ "id long not null primary key," + "nome_do_cargo varchar(255) not null )");
		} finally {
			criarTabela.close();
		}
	}

	private static Connection abrirConexao() throws Exception {
		Connection c = DriverManager.getConnection("jdbc:h2:~/func", "sa", "");
		return c;
	}
	

	private static void truncarTabelaFuncionarios(Connection conexão) throws Exception {
		Statement truncarTabela = null;
		
		try {
			truncarTabela = conexão.createStatement();
			truncarTabela.execute("drop table funcionarios_de_uma_empresa");
		} finally {
			truncarTabela.close();
		}
	}

}
