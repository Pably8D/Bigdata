package it.bigdata.web.mb;

import it.bigdata.dto.Constants;
import it.bigdata.dto.Tag;
import it.bigdata.dto.constants.Tables;
import it.bigdata.ejb.service.cockroachdb.CockroachdbCrud;
import it.bigdata.ejb.service.mongo.MongoCRUDImp;
import it.bigdata.ejb.service.mysql.MySqlCrud;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.SlideEndEvent;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "oltpMB")
@SessionScoped
public class OltpMB extends BaseMB {
	private static final long serialVersionUID = -6617081703017264124L;
	@EJB
	MySqlCrud mySqlCrudService;
	@EJB
	MongoCRUDImp mongoServices;
	@EJB
	CockroachdbCrud cockroachdbService;
	
	private List<Tag> list;
	private List<String> listTable;
	private String tableSelected;
	private List<SelectItem> transactionsList;
	private int numTransactionSelected;
	private List<String> operationsList;
	private String operationSelected;
	private HashMap<String, String> column2Value;
	private List<String> columnsSelected;
	private DynaFormModel model;
	private DynaFormModel modelForWhere;
	private HashMap<String, ArrayList<Long>> db2Time;
	private HashMap<String, String> column2ValueWhere;
	private Long maxNumeberTransantion;
	private BarChartModel barModel;

	public OltpMB() {
		this.numTransactionSelected = Constants.CENTO;
		this.operationSelected = "INSERT";
	}

	@PostConstruct
	public void init() {
		this.numTransactionSelected = new Integer(0);
		this.createTransactionsList();
		this.createOperationsList();
		this.populateTableList();
		this.model = new DynaFormModel();
		this.modelForWhere = new DynaFormModel();
		this.barModel = new BarChartModel();
		this.list = new ArrayList(this.mongoServices.getAllTag());
		this.db2Time = new HashMap();
		this.db2Time = new HashMap();
		this.db2Time.put("SQL", new ArrayList());
		this.db2Time.put("NOSQL", new ArrayList());
		this.db2Time.put("NEWSQL", new ArrayList());
	}

	private void populateTableList() {
		this.listTable = new ArrayList();
		this.listTable.addAll(Tables.getNamesOfTables());
		this.column2Value = new HashMap();
		this.columnsSelected = new ArrayList();
		this.column2ValueWhere = new HashMap();
	}

	public void getColumnsForTable() {
		this.column2Value.clear();
		this.column2ValueWhere.clear();
		if (Tables.getNamesOfTables().contains(this.tableSelected)) {
			String[] var4;
			int var3 = (var4 = Tables.valueOf(this.tableSelected).getCloumns()).length;

			for (int var2 = 0; var2 < var3; ++var2) {
				String col = var4[var2];
				if (!col.equalsIgnoreCase("id") && !col.equalsIgnoreCase(Tables.CATEGORY.getName())) {
					this.column2Value.put(col, "");
					this.column2ValueWhere.put(col, "");
				}
			}
		}

		this.createInputs();
	}

	public void numberTransantionForTable() {
		if (this.operationSelected.equals("DELETE") && this.tableSelected != null) {
//			this.maxNumeberTransantion = this.mongoServices.searchFakeData(this.tableSelected);
			this.numTransactionSelected = 0;
		} else {
			this.maxNumeberTransantion = Constants.DIECIMILA.longValue();
		}

	}

	public void createInputs() {
		this.model.getRegularRows().clear();
		DynaFormRow row = this.model.createRegularRow();
		this.model.getControls().clear();
		DynaFormControl control = null;
		DynaFormControl controlSelect = null;
		DynaFormLabel label = null;
		Iterator var6 = this.column2Value.keySet().iterator();

		while (var6.hasNext()) {
			String col = (String) var6.next();
			if (!col.equalsIgnoreCase(Tables.TAGS.getName()) && !col.equalsIgnoreCase(Tables.CATEGORY.getName())) {
				Dato d = new Dato(col, new Boolean(false));
				row.addControl(d, "input");
			}
		}

		if (this.tableSelected != null && this.tableSelected.equalsIgnoreCase(Tables.QUESTION.getName())) {
			Dato d = new Dato("", false);
			d.setRendered(new Boolean(true));
			d.setColumn(Tables.TAGS.getName());

			row.addControl(d, "select");
		}

	}

	public void oltpActionMethod() {
		Iterator var2 = this.model.getControls().iterator();

		while (var2.hasNext()) {
			DynaFormControl dynaFormControl = (DynaFormControl) var2.next();
			Dato d = (Dato) dynaFormControl.getData();
			if (d.getValue() != null) {
				this.column2Value.put(d.getColumn(), (String) d.getValue());
			}
		}

		if (this.operationSelected.equals("INSERT")) {
			((ArrayList) this.db2Time.get("SQL")).add(this.mySqlCrudService.insert(this.numTransactionSelected, this.column2Value, this.tableSelected));
			((ArrayList) this.db2Time.get("NOSQL")).add(this.mongoServices.insert(this.numTransactionSelected, this.column2Value, this.tableSelected));
			((ArrayList) this.db2Time.get("NEWSQL")).add(this.cockroachdbService.insert(this.numTransactionSelected, this.column2Value, this.tableSelected));
		} else if (this.operationSelected.equals("UPDATE")) {
			this.column2ValueWhere.put("fake", "1");
			((ArrayList) this.db2Time.get("SQL")).add(this.mySqlCrudService.update(this.numTransactionSelected,this.column2Value, this.column2ValueWhere, this.tableSelected));
			((ArrayList) this.db2Time.get("NOSQL")).add(this.mongoServices.update(this.numTransactionSelected,this.column2Value, this.column2ValueWhere, this.tableSelected));
			((ArrayList) this.db2Time.get("NEWSQL")).add(this.cockroachdbService.update(this.numTransactionSelected,this.column2Value, this.column2ValueWhere, this.tableSelected));
		} else {
			((ArrayList) this.db2Time.get("SQL")).add(this.mySqlCrudService.delete(this.tableSelected));
			((ArrayList) this.db2Time.get("NOSQL")).add(this.mongoServices.delete(this.tableSelected));
			((ArrayList) this.db2Time.get("NEWSQL")).add(this.cockroachdbService.delete(this.tableSelected));
		}

		this.createBarModel();
	}

	private void createBarModel() {
		this.barModel.clear();
		this.barModel.setTitle("Bar Chart");
		this.barModel.setLegendPosition("ne");
		this.barModel.setBarWidth(40);
		this.barModel.setBarMargin(5);
		this.barModel.setAnimate(true);
		this.barModel.setStacked(false);
		this.barModel.setExtender("metroExtenderSimulazioneSintesiBar");
		this.barModel.setSeriesColors("58BA27,FFCC33,F74A4A,F52F2F,A30303");

		Axis xAxis = this.barModel.getAxis(AxisType.X);
		xAxis.setLabel("Tipo DB");
		Axis yAxis = this.barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Tempo");
		yAxis.setMin(0);
		ArrayList<Long> times = new ArrayList();
		Iterator var5 = this.db2Time.keySet().iterator();

		while (var5.hasNext()) {
			String i = (String) var5.next();
			times.addAll((Collection) this.db2Time.get(i));
		}

		yAxis.setMax((Long) Collections.max(times) + 50L);
		this.initBarModel();
	}

	private void initBarModel() {

		ChartSeries db = new ChartSeries();
		Iterator var3 = this.db2Time.keySet().iterator();

		while (var3.hasNext()) {
			String tipoDB = (String) var3.next();
			int j = 1;
			Iterator var6 = ((ArrayList) this.db2Time.get(tipoDB)).iterator();

			while (var6.hasNext()) {
				Long i = (Long) var6.next();
				if (tipoDB != null && !((ArrayList) this.db2Time.get(tipoDB)).isEmpty()) {

					db.set(tipoDB + " " + j + "�", i);
					++j;
				}
			}
		}

		this.barModel.addSeries(db);
	}

	public void onSlideEnd(SlideEndEvent event) {
		if (event != null) {
			this.numTransactionSelected = event.getValue();
		}

	}

	private void createOperationsList() {
		this.operationsList = new ArrayList<String>();
		this.operationsList.add("INSERT");
		this.operationsList.add("UPDATE");
		this.operationsList.add("DELETE");
	}

	private void createTransactionsList() {
		this.transactionsList = new ArrayList();
		SelectItem si = new SelectItem(Constants.CENTO, Constants.CENTO.toString());
		this.transactionsList.add(si);
		si = new SelectItem(Constants.MILLE, Constants.MILLE.toString());
		this.transactionsList.add(si);
		si = new SelectItem(Constants.DIECIMILA, Constants.DIECIMILA.toString());
		this.transactionsList.add(si);
	}

	public List<SelectItem> getTransactionsList() {
		return this.transactionsList;
	}

	public void setTransactionsList(List<SelectItem> transactionsList) {
		this.transactionsList = transactionsList;
	}

	public int getNumTransactionSelected() {
		return this.numTransactionSelected;
	}

	public void setNumTransactionSelected(int numTransactionSelected) {
		this.numTransactionSelected = numTransactionSelected;
	}

	public List<String> getOperationsList() {
		return this.operationsList;
	}

	public void setOperationsList(List<String> operationsList) {
		this.operationsList = operationsList;
	}

	public String getOperationSelected() {
		return this.operationSelected;
	}

	public void setOperationSelected(String operationSelected) {
		this.operationSelected = operationSelected;
	}

	public MongoCRUDImp getMongoServices() {
		return this.mongoServices;
	}

	public void setMongoServices(MongoCRUDImp mongoServices) {
		this.mongoServices = mongoServices;
	}

	public List<String> getListTable() {
		return this.listTable;
	}

	public void setListTable(List<String> listTable) {
		this.listTable = listTable;
	}

	public String getTableSelected() {
		return this.tableSelected;
	}

	public void setTableSelected(String tableSelected) {
		this.tableSelected = tableSelected;
	}

	public HashMap<String, String> getColumn2Value() {
		return this.column2Value;
	}

	public void setColumn2Value(HashMap<String, String> column2Value) {
		this.column2Value = column2Value;
	}

	public List<String> getColumnsSelected() {
		return this.columnsSelected;
	}

	public void setColumnsSelected(List<String> columnsSelected) {
		this.columnsSelected = columnsSelected;
	}

	public DynaFormModel getModel() {
		return this.model;
	}

	public void setModel(DynaFormModel model) {
		this.model = model;
	}

	public List<Tag> getList() {
		return this.list;
	}

	public void setList(List<Tag> list) {
		this.list = list;
	}

	public DynaFormModel getModelForWhere() {
		return this.modelForWhere;
	}

	public void setModelForWhere(DynaFormModel modelForWhere) {
		this.modelForWhere = modelForWhere;
	}

	public HashMap<String, String> getColumn2ValueWhere() {
		return this.column2ValueWhere;
	}

	public void setColumn2ValueWhere(HashMap<String, String> column2ValueWhere) {
		this.column2ValueWhere = column2ValueWhere;
	}

	public Long getMaxNumeberTransantion() {
		return this.maxNumeberTransantion;
	}

	public void setMaxNumeberTransantion(Long maxNumeberTransantion) {
		this.maxNumeberTransantion = maxNumeberTransantion;
	}

	public HashMap<String, ArrayList<Long>> getDb2Time() {
		return this.db2Time;
	}

	public void setDb2Time(HashMap<String, ArrayList<Long>> db2Time) {
		this.db2Time = db2Time;
	}

	public BarChartModel getBarModel() {
		return this.barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
}
