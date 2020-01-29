package it.bigdata.web.mb;

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
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "olapMB")
@SessionScoped
public class OlapMB extends BaseMB {
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> column2Value;
	private List<String> listTable;
	private List<String> tableSelected;
	private HashMap<String, String> column2ValueWhere;
	private List<String> columnsSelected;
	private List<Tag> list;
	private HashMap<String, LinkedList<Long>> db2Time;
	private DynaFormModel model;
	private DynaFormModel modelForWhere;
	@EJB
	MongoCRUDImp mongoServices;
	@EJB
	MySqlCrud mySqlCrudService;
	private BarChartModel barModel;
	@EJB
	CockroachdbCrud cockroachdbService;

	@PostConstruct
	public void init() {
		this.populateTableList();
		this.model = new DynaFormModel();
		this.barModel = new BarChartModel();
		this.modelForWhere = new DynaFormModel();
		this.list = new ArrayList<Tag>(this.mongoServices.getAllTag());
		this.db2Time = new HashMap<String, LinkedList<Long>>();
		this.db2Time.put("SQL", new LinkedList<Long>());
		this.db2Time.put("NOSQL", new LinkedList<Long>());
		this.db2Time.put("NEWSQL", new LinkedList<Long>());
	}

	private void populateTableList() {
		this.listTable = new ArrayList<String>();
		this.listTable.addAll(Tables.getNamesOfTables());
		this.column2Value = new HashMap<String, String>();
		this.columnsSelected = new ArrayList<String>();
		this.column2ValueWhere = new HashMap<String, String>();
	}

	public void getColumnsForTable() {
		this.column2Value.clear();
		this.column2ValueWhere.clear();
		this.columnsSelected.clear();
		if (this.tableSelected != null) {
			Iterator var2 = this.tableSelected.iterator();

			while (var2.hasNext()) {
				String nameTable = (String) var2.next();
				String[] var6;
				int var5 = (var6 = Tables.valueOf(nameTable).getCloumns()).length;

				for (int var4 = 0; var4 < var5; ++var4) {
					String col = var6[var4];
					if (!col.equalsIgnoreCase("id")) {
						this.column2Value.put(col, "");
						this.column2ValueWhere.put(col, "");
					}
				}
			}
		}

//		this.createInputs();
		this.createWhereInputs();
	}

	private void createWhereInputs() {
		this.modelForWhere.getRegularRows().clear();
		this.modelForWhere.getControls().clear();
		DynaFormControl control = null;
		DynaFormLabel label = null;
		Iterator var5 = this.tableSelected.iterator();

		while (var5.hasNext()) {
			String nameTable = (String) var5.next();
			DynaFormRow row = this.modelForWhere.createRegularRow();
			String[] var10;
			int var9 = (var10 = Tables.valueOf(nameTable).getCloumns()).length;

			for (int var8 = 0; var8 < var9; ++var8) {
				String col = var10[var8];
				if (!col.equalsIgnoreCase("id") && !col.equalsIgnoreCase(Tables.CATEGORY.getName())) {
					Dato d;
					if (!col.equalsIgnoreCase(Tables.TAGS.getName())) {
						d = new Dato(col, new Boolean(false));
						label = row.addLabel(col);
						control = row.addControl(d, "input");
						label.setForControl(control);
					} else {
						d = new Dato("", false);
						label = row.addLabel(Tables.TAGS.getName());
						d.setColumn(Tables.TAGS.getName());
						d.setColumn(Tables.TAGS.getName());
						row.addControl(d, "select");
					}
				}
			}
		}

	}

	public void createInputs() {
		if (this.model.getControls() != null)
			this.model.getControls().clear();
		if (this.model.getRegularRows() != null)
			this.model.getRegularRows().clear();
		if (this.model.getExtendedRows() != null)
			this.model.getExtendedRows().clear();
		DynaFormRow row = new DynaFormRow();
		Iterator var5 = this.tableSelected.iterator();
		row.addModel(this.model);
		while (var5.hasNext()) {
			String nameTable = (String) var5.next();
			String[] var9;
			int var8 = (var9 = Tables.valueOf(nameTable).getCloumns()).length;

			for (int var7 = 0; var7 < var8; ++var7) {
				String col = var9[var7];
				if (!col.equalsIgnoreCase("id")) {
					Dato d;
					if (!col.equalsIgnoreCase(Tables.TAGS.getName())) {
						d = new Dato(col, new Boolean(false));
						row.addControl(d, "input");
					} else {
						d = new Dato("", false);
						d.setRendered(new Boolean(true));
						d.setColumn(Tables.TAGS.getName());
						row.addControl(d, "select");
					}
				}
			}
		}

		this.model.getRegularRows().add(row);
	}

	public void oltpActionMethod() {
		HashMap<String, HashMap<String, String>> tables2Columnvalue = new HashMap<String, HashMap<String, String>>();
		Iterator var3 = this.tableSelected.iterator();

		String nameTable;
		while (var3.hasNext()) {
			nameTable = (String) var3.next();
			Iterator var5 = this.modelForWhere.getControls().iterator();

			while (var5.hasNext()) {
				DynaFormControl dynaFormControl = (DynaFormControl) var5.next();
				Dato d = (Dato) dynaFormControl.getData();
				if (d.getValue() != null) {
					this.column2Value.put(d.getColumn(), (String) d.getValue());
				} else {
					this.column2Value.put(d.getColumn(), (String) null);
				}
			}
		}

		var3 = this.tableSelected.iterator();

		while (var3.hasNext()) {
			nameTable = (String) var3.next();
			tables2Columnvalue.put(nameTable, new HashMap<String, String>());
			String[] var7;
			int var10 = (var7 = Tables.valueOf(nameTable).getCloumns()).length;

			for (int var9 = 0; var9 < var10; ++var9) {
				String col = var7[var9];
				if (this.column2Value.get(col) != null && !((String) this.column2Value.get(col)).isEmpty()) {
					((HashMap) tables2Columnvalue.get(nameTable)).put(col, (String) this.column2Value.get(col));
				}
			}
		}

		((LinkedList) this.db2Time.get("NOSQL")).add(this.executeNoSql(tables2Columnvalue, this.columnsSelected));
		((LinkedList) this.db2Time.get("SQL")).add(this.mySqlCrudService.sql(tables2Columnvalue, this.extracted(tables2Columnvalue)));
		((LinkedList) this.db2Time.get("NEWSQL")).add(this.cockroachdbService.sql(tables2Columnvalue, this.extracted(tables2Columnvalue)));
		this.createBarModel();
	}

	private void createBarModel() {
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
		ArrayList<Long> times = new ArrayList<Long>();
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
			Iterator var6 = ((LinkedList) this.db2Time.get(tipoDB)).iterator();

			while (var6.hasNext()) {
				Long i = (Long) var6.next();
				if (tipoDB != null && !((LinkedList) this.db2Time.get(tipoDB)).isEmpty()) {
					db.setLabel(tipoDB + " " + j + "°");
					db.set(tipoDB + " " + j + "°", i);
					++j;
				}
			}
		}

		this.barModel.addSeries(db);
	}

	
	
	private Long executeNoSql(HashMap<String, HashMap<String, String>> tables2Columnvalue,List<String> columnsSelected2) {
		HashMap map=new HashMap();
		map = this.extracted(tables2Columnvalue);
		return this.mongoServices.sql(tables2Columnvalue, map);
	}

	private HashMap<String, List<String>> extracted(HashMap<String, HashMap<String, String>> tables2Columnvalue) {
		HashMap<String, List<String>> map = new HashMap();
		Iterator var4 = tables2Columnvalue.keySet().iterator();

		String table;
		while (var4.hasNext()) {
			table = (String) var4.next();
			map.put(table, new ArrayList());
		}

		var4 = tables2Columnvalue.keySet().iterator();

		while (var4.hasNext()) {
			table = (String) var4.next();
			Iterator var6 = this.columnsSelected.iterator();

			while (var6.hasNext()) {
				String col = (String) var6.next();
				if (Tables.getColumnsOf(table).contains(col) ) {
					((List) map.get(table)).add(col);
				}
			}
		}
		
		for(String t: map.keySet()){
			if(map.get(t).isEmpty()) {
				map.remove(t);
			}
		}

		return map;
	}

	public HashMap<String, String> getColumn2Value() {
		return this.column2Value;
	}

	public void setColumn2Value(HashMap<String, String> column2Value) {
		this.column2Value = column2Value;
	}

	public List<String> getListTable() {
		return this.listTable;
	}

	public void setListTable(List<String> listTable) {
		this.listTable = listTable;
	}

	public List<String> getTableSelected() {
		return this.tableSelected;
	}

	public void setTableSelected(List<String> tableSelected) {
		this.tableSelected = tableSelected;
	}

	public HashMap<String, String> getColumn2ValueWhere() {
		return this.column2ValueWhere;
	}

	public void setColumn2ValueWhere(HashMap<String, String> column2ValueWhere) {
		this.column2ValueWhere = column2ValueWhere;
	}

	public List<String> getColumnsSelected() {
		return this.columnsSelected;
	}

	public void setColumnsSelected(List<String> columnsSelected) {
		this.columnsSelected = columnsSelected;
	}

	public List<Tag> getList() {
		return this.list;
	}

	public void setList(List<Tag> list) {
		this.list = list;
	}

	public HashMap<String, LinkedList<Long>> getDb2Time() {
		return this.db2Time;
	}

	public void setDb2Time(HashMap<String, LinkedList<Long>> db2Time) {
		this.db2Time = db2Time;
	}

	public DynaFormModel getModel() {
		return this.model;
	}

	public void setModel(DynaFormModel model) {
		this.model = model;
	}

	public DynaFormModel getModelForWhere() {
		return this.modelForWhere;
	}

	public void setModelForWhere(DynaFormModel modelForWhere) {
		this.modelForWhere = modelForWhere;
	}

	public MongoCRUDImp getMongoServices() {
		return this.mongoServices;
	}

	public void setMongoServices(MongoCRUDImp mongoServices) {
		this.mongoServices = mongoServices;
	}

	public BarChartModel getBarModel() {
		return this.barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
}
