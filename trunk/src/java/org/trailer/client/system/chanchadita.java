package org.trailer.client.system;

import com.gwtext.client.core.SortDir;
import com.gwtext.client.core.TextAlign;
import com.gwtext.client.data.*;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.GroupingView;

public class chanchadita extends Panel {

    public chanchadita() {

        this.setClosable(true);
        this.setId("TPfunchancaha");
        setIconCls("tab-icon");
        setAutoScroll(true);
        setTitle("Chancha");
        onModuleLoad();
    }

    public void onModuleLoad() {
        Panel panel = new Panel();
        panel.setBorder(false);
        panel.setPaddings(15);

        MemoryProxy proxy = new MemoryProxy(getCompanyData());
        RecordDef recordDef = new RecordDef(
                new FieldDef[]{
                    new StringFieldDef("idalmacen"),
                    new StringFieldDef("nombre"),
                    new FloatFieldDef("cantidad"),
                    new StringFieldDef("ubicacion"),
                    new StringFieldDef("idproducto"),
                    new StringFieldDef("nombreP")
                });

        ArrayReader reader = new ArrayReader(recordDef);

        GroupingStore store = new GroupingStore();
        store.setReader(reader);
        store.setDataProxy(proxy);
        store.setSortInfo(new SortState("idproducto", SortDir.ASC));
        store.setGroupField("idproducto");
        store.load();
        ColumnConfig cantidad1017 = new ColumnConfig("Cantidad", "cantidad", 100, true);
        cantidad1017.setAlign(TextAlign.RIGHT);
        cantidad1017.setEditor(new GridEditor(new TextField()));

        ColumnConfig ubicacion1017 = new ColumnConfig("Ubicacion", "ubicacion", 65);
        ubicacion1017.setEditor(new GridEditor(new TextField()));

        ColumnConfig[] columns = new ColumnConfig[]{
            //column ID is company which is later used in setAutoExpandColumn
            new ColumnConfig("Id Almacen", "idalmacen", 35, true, null, "idalmacen"),
            new ColumnConfig("Almacen", "nombre", 65),
            cantidad1017,
            ubicacion1017,
            new ColumnConfig("Producto", "idproducto", 65)
        };

        ColumnModel columnModel = new ColumnModel(columns);

        GridPanel grid = new GridPanel();
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setAutoExpandColumn("idproducto");
        grid.setTitle("Grid Events");
        grid.setHeight(350);
        grid.setWidth(600);

        GroupingView gridView = new GroupingView();
        gridView.setForceFit(true);
        //gridView.setGroupTextTpl("{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"Items\" : \"Item\"]})");
        gridView.setGroupTextTpl("{[values.rs[0].data[\"nombreP\"]]}");
        grid.setView(gridView);
        grid.setFrame(true);
        grid.setWidth(520);
        grid.setHeight(400);
        grid.setCollapsible(true);
        grid.setAnimCollapse(false);
        grid.setTitle("Grouping Example");
        grid.setIconCls("grid-icon");

        add(grid);
    }

    private Object[][] getCompanyData() {
        return new Object[][]{
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1000", "AAAAAA"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1000", "AAAAAA"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1000", "AAAAAA"},
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1001", "AAAAAB"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1001", "AAAAAB"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1001", "AAAAAB"},
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1002", "AAAAAC"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1002", "AAAAAC"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1002", "AAAAAC"},
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1003", "AAAAAD"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1003", "AAAAAD"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1003", "AAAAAD"},
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1004", "AAAAAE"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1004", "AAAAAE"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1004", "AAAAAE"},
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1005", "AAAAAF"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1005", "AAAAAF"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1005", "AAAAAF"},
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1006", "AAAAAG"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1006", "AAAAAG"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1006", "AAAAAG"},
                    new Object[]{"alm1000", "Tienda1", new Double(200), "", "pro1007", "AAAAAH"},
                    new Object[]{"alm1001", "Tienda2", new Double(200), "", "pro1007", "AAAAAH"},
                    new Object[]{"alm1002", "Tienda3", new Double(200), "", "pro1007", "AAAAAH"}
                };
    }
}  