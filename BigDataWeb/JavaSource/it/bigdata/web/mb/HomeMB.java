package it.bigdata.web.mb;

import it.bigdata.dto.OltpStatisticDTO;
import it.bigdata.ejb.service.OltpService;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean(
   name = "homeMB"
)
@SessionScoped
public class HomeMB extends BaseMB {
   @EJB
   OltpService oltpService;
   private List<OltpStatisticDTO> oltpStatisticsList;
   private LineChartModel oltpInsertModel;
   private LineChartModel oltpUpdateModel;
   private LineChartModel oltpDeleteModel;

   @PostConstruct
   public void init() {
      this.oltpStatisticsList = this.oltpService.getOltpStatistics();
      this.oltpInsertModel = this.createModel("INSERT");
      this.oltpUpdateModel = this.createModel("UPDATE");
      this.oltpDeleteModel = this.createModel("DELETE");
   }

   private LineChartModel createModel(String operation) {
      LineChartModel model = new LineChartModel();
      model.setExtender("skinChart");
      model.setTitle(operation);
      model.setLegendPosition("e");
      model.setShowPointLabels(true);
      model.getAxes().put(AxisType.X, new CategoryAxis("N. Transactions"));
      model.getAxis(AxisType.Y).setLabel("Time in seconds");
      model.getAxis(AxisType.X).setMin(0);
      model.getAxis(AxisType.X).setMax(10000);
      ChartSeries sql = new ChartSeries();
      ChartSeries noSql = new ChartSeries();
      ChartSeries newSql = new ChartSeries();
      sql.setLabel("SQL");
      noSql.setLabel("NOSQL");
      newSql.setLabel("NEWSQL");
      Iterator var7 = this.oltpStatisticsList.iterator();

      while(var7.hasNext()) {
         OltpStatisticDTO dto = (OltpStatisticDTO)var7.next();
         if (dto.getOperation().equals(operation)) {
            if (dto.getDb().equals("SQL")) {
               sql.set(dto.getNumTransactions(), dto.getSeconds());
            } else if (dto.getDb().equals("NOSQL")) {
               noSql.set(dto.getNumTransactions(), dto.getSeconds());
            } else if (dto.getDb().equals("NEWSQL")) {
               newSql.set(dto.getNumTransactions(), dto.getSeconds());
            }
         }
      }

      model.addSeries(sql);
      model.addSeries(noSql);
      model.addSeries(newSql);
      return model;
   }

   public LineChartModel getOltpInsertModel() {
      return this.oltpInsertModel;
   }

   public void setOltpInsertModel(LineChartModel oltpInsertModel) {
      this.oltpInsertModel = oltpInsertModel;
   }

   public LineChartModel getOltpUpdateModel() {
      return this.oltpUpdateModel;
   }

   public void setOltpUpdateModel(LineChartModel oltpUpdateModel) {
      this.oltpUpdateModel = oltpUpdateModel;
   }

   public LineChartModel getOltpDeleteModel() {
      return this.oltpDeleteModel;
   }

   public void setOltpDeleteModel(LineChartModel oltpDeleteModel) {
      this.oltpDeleteModel = oltpDeleteModel;
   }
}
