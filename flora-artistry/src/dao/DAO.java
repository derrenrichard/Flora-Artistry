package dao;

import database.Connect;

public abstract class DAO {

	Connect con;

	public DAO() {
		this.con = Connect.getInstance();
	}
	
}
