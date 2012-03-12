/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.cortes;

import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import com.gwtext.client.data.*;
import org.trailer.client.util.Utils;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;

/**
 *
 * @author example
 */
public class ListaItems {

    private EditorGridPanel grid;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton eliminarProducto;
    protected ExtElement ext_element;
    private CheckboxSelectionModel cbSelectionModel;
    private Store store;
    private ColumnConfig idColumn;
    private BaseColumnConfig[] columns;
    private ColumnModel columnModel;
    protected String buscaCodigo;
    protected String buscarNombre;
    private ToolbarButton buscar;
    private DataProxy dataProxy;
    private JsonReader reader;
    PagingToolbar pagingToolbar;
    String selecionado = "";
    private Panel panel;
    private ColumnConfig udstColumn;
    private ColumnConfig udsColumn;
    private ColumnConfig telaColumn;
    private ColumnConfig corteColumn;
    private ColumnConfig hojaColumn;
    private ColumnConfig uds1Column;
    private ColumnConfig nombreColumn;
    private ColumnConfig nombretColumn;
    private ColumnConfig totalColumn;
    String[] nombreComlumns = {"id", "idtela", "idcorte", "uds", "nombrei", "nombret", "nombrec", "tela", "hoja", "uds1", "total", "totalunidades"};
    private RecordDef recordDef;

    public void onModuleLoad() {
        panel = new Panel();
        //panel.setId("panel-lista-productosproveedor");

        dataProxy = new ScriptTagProxy("");
        recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef(nombreComlumns[0]),
                    new StringFieldDef(nombreComlumns[1]),
                    new StringFieldDef(nombreComlumns[2]),
                    new StringFieldDef(nombreComlumns[3]),
                    new StringFieldDef(nombreComlumns[4]),
                    new StringFieldDef(nombreComlumns[5]),
                    new StringFieldDef(nombreComlumns[6]),
                    new StringFieldDef(nombreComlumns[7]),
                    new StringFieldDef(nombreComlumns[8]),
                    new StringFieldDef(nombreComlumns[9]),
                    new StringFieldDef(nombreComlumns[10]),
                    new StringFieldDef(nombreComlumns[11])
                });
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");
        store = new Store(dataProxy, reader, true);
//          String[] nombreComlumns = {"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"};


        udsColumn = new ColumnConfig("UDS", nombreComlumns[3], 50, false);
        nombreColumn = new ColumnConfig("Nombre Item", nombreComlumns[4], 200, false);
        nombretColumn = new ColumnConfig("Nombre Tela", nombreComlumns[5], 200, false);
        corteColumn = new ColumnConfig("Molde", nombreComlumns[6], 200, false);
        telaColumn = new ColumnConfig("Tela.", nombreComlumns[7], 100, false);
        hojaColumn = new ColumnConfig("Hoja", nombreComlumns[8], 100, false);
        uds1Column = new ColumnConfig("Para (uds)", nombreComlumns[9], 100, false);
        totalColumn = new ColumnConfig("Total Tela", nombreComlumns[10], 100, false);
        udstColumn = new ColumnConfig("Uds Total", nombreComlumns[11], 100, false);

        NumberField num_field1 = new NumberField();
        num_field1.setAllowBlank(false);
        num_field1.setAllowNegative(false);
        num_field1.setSelectOnFocus(true);
        // num_field1.setMaxValue(1000);

        NumberField num_field2 = new NumberField();
        num_field2.setAllowBlank(false);
        num_field2.setAllowNegative(false);
        num_field2.setSelectOnFocus(true);
        //num_field2.setMaxValue(1000);
        NumberField num_field3 = new NumberField();
        num_field3.setAllowBlank(false);
        num_field3.setAllowNegative(false);
        num_field3.setSelectOnFocus(true);

//        precioBsColumn.setEditor(new GridEditor(num_field1));
        hojaColumn.setEditor(new GridEditor(num_field1));
        telaColumn.setEditor(new GridEditor(num_field2));
        uds1Column.setEditor(new GridEditor(num_field3));


        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    udsColumn,
                    nombreColumn,
                    nombretColumn,
                    corteColumn,
                    telaColumn,
                    hojaColumn,
                    uds1Column,
                    totalColumn,
                    udstColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        //grid.setId("grid-lista-productosproveedor");
        grid.setWidth("100%");
        grid.setHeight(ALTO - 220);
        grid.setTitle("Lista de item corte");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(true);
        grid.setLoadMask(true);
        grid.setSelectionModel(new RowSelectionModel());
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setIconCls("grid-icon");
        grid.setAutoScroll(true);
        grid.setClicksToEdit(1);

        eliminarProducto = new ToolbarButton("Quitar");
        eliminarProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Quitar Item(s)");
        //tipsConfig.setTitle("Tip Title");
        eliminarProducto.setTooltip(tipsConfig2);

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(false);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarProducto);

        grid.setBottomToolbar(pagingToolbar);

//        aniadirListenersBuscador();
//
//        aniadirListenersBuscadoresText();

        aniadirListenersProducto();
        panel.add(grid);
    }

    public EditorGridPanel getGrid() {
        return grid;
    }

    public void setGrid(EditorGridPanel grid) {
        this.grid = grid;
    }

    public Panel getPanel() {
        return panel;
    }

    private void aniadirListenersProducto() {
        //**************************************************
        //***********ELIMINAR PRODUCTO
        //**************************************************

        eliminarProducto.addListener(new ButtonListenerAdapter() {

            private boolean procederAEliminar;
            int repeat = 0;

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("id");
                    grid.stopEditing();
                    store.remove(cbSelectionModel.getSelected());
                    grid.startEditing(0, 0);
                } else {
                    MessageBox.alert("No hay Items selecionado para eliminar y/o selecciono mas de uno.");
                }
                eliminarProducto.setPressed(false);
            }
        });



        //**************************************************
        //*********** LISTENERS DE LA TABLA
        //**************************************************
        grid.addListener(
                new PanelListenerAdapter() {

                    @Override
                    public void onRender(Component component) {
                        store.load(0, 100);
                    }
                });
        grid.addGridCellListener(
                new GridCellListenerAdapter() {

                    @Override
                    public void onCellClick(GridPanel grid, int rowIndex, int colIndex, EventObject e) {
                        if (grid.getColumnModel().getDataIndex(colIndex).equals("indoor")) {
                            Record record = grid.getStore().getAt(rowIndex);
                            record.set("indoor", !record.getAsBoolean("indoor"));
                        }

                    }
                });
//        grid.addGridRowListener(new GridRowListenerAdapter() {
//
//            @Override
//            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
//                Record rs = grid.getStore().getRecordAt(rowIndex);
//                quitarEsteItem(rs);
//            }
//        });
    }

    private void quitarEsteItem(Record quitar) {

        store.remove(quitar);
        grid.setStore(store);
        grid.startEditing(0, 0);
    }

    public RecordDef getRecordDef() {
        return recordDef;
    }

    public void LimpiarGrid() {

        store.removeAll();
        grid.setStore(store);
        grid.reconfigure(store, columnModel);

    }

    public Store getStore() {
        return store;
    }

    public Record[] getRecords() {
        Record[] records = cbSelectionModel.getSelections();
        return records;
    }

    public CheckboxSelectionModel getSelectionModel() {
        return cbSelectionModel;
    }

    public ColumnModel getColumnModel() {
        return columnModel;
    }
}