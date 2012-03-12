/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.distribuciones;

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
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.grid.RowSelectionModel;

/**
 *
 * @author example
 */
public class ListaItem {

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
    private Toolbar pagingToolbar;
    String selecionado = "";
    private Panel panel;
    private ColumnConfig detalleColumn;
    private ColumnConfig telaColumn;
    private ColumnConfig plazoColumn;
    private ColumnConfig encargadoColumn;
    private ColumnConfig cantidadColumn;
    String[] nombreComlumns = {"id", "idencargado", "detalle", "encargado", "cantidad", "plazo"};
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
                    new StringFieldDef(nombreComlumns[5])
                });
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");
        store = new Store(dataProxy, reader, true);
//          String[] nombreComlumns = {"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"};


        detalleColumn = new ColumnConfig("Detalle Item", nombreComlumns[2], 200, false);
        encargadoColumn = new ColumnConfig("Encargado", nombreComlumns[3], 200, false);
        cantidadColumn = new ColumnConfig("Cantidad", nombreComlumns[4], 80, false);
        plazoColumn = new ColumnConfig("Fecha Plazo", nombreComlumns[5], 100, false);


        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    detalleColumn,
                    encargadoColumn,
                    cantidadColumn,
                    plazoColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        //grid.setId("grid-lista-productosproveedor");
        grid.setWidth(620);
        grid.setHeight(ALTO - 220);
        grid.setTitle("Lista de item OP");
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

        pagingToolbar = new Toolbar();
        pagingToolbar.addFill();
        pagingToolbar.setPosition(0, 0);
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