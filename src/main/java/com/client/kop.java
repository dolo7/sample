package com.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.*;
import com.smartgwt.client.data.fields.*;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.LinkedList;
import java.util.List;


class ItemSupplyLocalDS extends DataSource {

    private static ItemSupplyLocalDS instance = null;

    private List<Record> data = new LinkedList<Record>();

    public static ItemSupplyLocalDS getInstance() {
        if (instance == null) {
            instance = new ItemSupplyLocalDS("supplyItemLocalDS");
        }
        return instance;
    }

    public ItemSupplyLocalDS(String id) {
        //setID-boo-(id);poli
        //fix from iss53
        //nvkjsfvewiruberwiob
        DataSourceIntegerField pkField = new DataSourceIntegerField("itemID");
        pkField.setHidden(true);
        pkField.setPrimaryKey(true);

        DataSourceTextField itemNameField = new DataSourceTextField("itemName", "Item Name", 128, true);
        setFields(pkField, itemNameField);
        setClientOnly(true);

    }

    private void setData(){
        for (Record r : data) {
            addData(r);
        }
    }

    public void add(String name){
        int l = data.size();
        Record d = new Record();
        d.setAttribute("itemID", l);
        d.setAttribute("itemName", "name" + l);
        data.add(d);
        setData();
    }

}


public class kop implements EntryPoint {
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  private final Messages messages = GWT.create(Messages.class);

    private ListGrid createGrid(){
        final DataSource dataSource = ItemSupplyLocalDS.getInstance();

        final ListGrid listGrid = new ListGrid();
        listGrid.setWidth100();
        listGrid.setHeight(250);
        listGrid.setDataSource(dataSource);
        listGrid.setAutoFetchData(true);
        listGrid.setShowFilterEditor(true);
        listGrid.setFilterOnKeypress(true);

        ListGridField f1 = new ListGridField("itemName", "SJI", 800);
        listGrid.setFields(f1);
        return listGrid;
    }

  public void onModuleLoad() {
        final ListGrid grid = createGrid();

        VLayout pane = new VLayout();
      pane.setWidth100();
      IButton fBtn = new IButton("Fetch");
      fBtn.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent clickEvent) {
              ItemSupplyLocalDS ds = (ItemSupplyLocalDS) grid.getDataSource();
              ds.fetchData(null, new DSCallback() {
                  public void execute(DSResponse dsResponse, Object o, DSRequest dsRequest) {
                    grid.setData(dsResponse.getData());
                      grid.filterData();
                  }
              });
          }
      });

      IButton addBtn = new IButton("Add");
      addBtn.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent clickEvent) {
              ItemSupplyLocalDS ds = (ItemSupplyLocalDS) grid.getDataSource();
              ds.add("kop");
              grid.redraw();
          }
      });
      pane.addMember(fBtn);
        pane.addMember(addBtn);
      pane.addMember(grid);
        pane.draw();
  }
}
