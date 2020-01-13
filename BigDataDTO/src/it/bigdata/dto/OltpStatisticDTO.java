package it.bigdata.dto;

import java.io.Serializable;

public class OltpStatisticDTO implements Serializable {

	private String operation;
	private Integer seconds;
	private String db;
	private Integer numTransactions;
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Integer getSeconds() {
		return seconds;
	}
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public Integer getNumTransactions() {
		return numTransactions;
	}
	public void setNumTransactions(Integer numTransactions) {
		this.numTransactions = numTransactions;
	}
	
	
}
