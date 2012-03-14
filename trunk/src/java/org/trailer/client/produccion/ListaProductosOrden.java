/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.produccion;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.form.Field;
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
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.grid.GridView;
import com.gwtext.client.widgets.grid.RowParams;
import com.gwtext.client.widgets.layout.TableLayout;
import org.trailer.client.util.Conector;

/**
 *
 * @author example
 */
public class ListaProductosOrden {

    private EditorGridPanel grid;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton eliminarProducto;
    private ToolbarButton detalleProducto;
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
    private ColumnConfig cantidadColumn;
    private ColumnConfig tallaColumn;
    private ColumnConfig punitColumn;
    private ColumnConfig totalColumn;
    private ColumnConfig detalleColumn;
    String[] nombreComlumns = {"id", "detalle", "unidad", "cantidad", "preciounitario", "preciototal", "tela", "color", "detalle1", "detallebordado", "detallecostura", "estado"};
    private RecordDef recordDef;
    private TextField tex_detalle;
    private ComboBox com_telas;
    private ComboBox com_colores;
    private ComboBox com_estado;
    private TextArea txa_detalle;
    private TextArea txa_detallebordado;
    private TextField tex_descripcion;
    private Object[][] telas;
    private Object[][] colores;
    private String[] estados;
    private String tela;
    private String color;
    private String detalle;
    private String detallebordado;
    private String estado;
    private Button but_guardar;
    private Button but_duplicar;
    private TextArea txa_detallecostura;

    public void onModuleLoad(Object[][] telas, Object[][] colores) {
        panel = new Panel();
        this.colores = colores;
        this.telas = telas;
        FormPanel panel1 = new FormPanel();
        panel1.setAutoScroll(true);
        panel1.setTitle("Datos Item");
        panel1.setIconCls("tab-icon");
        panel1.setWidth(500);

        panel1.setHeight(300);

        tex_detalle = new TextField("Detalle Item", "detalle", 250);
        com_telas = new ComboBox("Tela", "tela", 250);
        com_colores = new ComboBox("Color", "color");
        txa_detalle = new TextArea("Detalle", "detalle");
        but_guardar = new Button("Guardar Item");
        but_duplicar = new Button("Duplicar Item");
        txa_detalle.setWidth(250);
        txa_detalle.setHeight(50);
        txa_detallebordado = new TextArea("Detalle Bordado", "detallebordado");
        txa_detallebordado.setWidth(250);
        txa_detallebordado.setHeight(50);
        txa_detallecostura = new TextArea("Detalle Serigrafiado", "detallecostura");
        txa_detallecostura.setWidth(250);
        txa_detallecostura.setHeight(50);
        com_estado = new ComboBox("Estado", "estado");
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
                    new StringFieldDef(nombreComlumns[11])});
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");
        store = new Store(dataProxy, reader, true);

        idColumn = new ColumnConfig("Id Producto", nombreComlumns[0], (ANCHO / 8), false);
        detalleColumn = new ColumnConfig("Detalle", nombreComlumns[1], 200, false, null, "detalle");
        cantidadColumn = new ColumnConfig("Cantidad", nombreComlumns[3], 100, false);
        tallaColumn = new ColumnConfig("Talla", nombreComlumns[2], 100, false);
        punitColumn = new ColumnConfig("P/Unit.", nombreComlumns[4], 100, false);
        totalColumn = new ColumnConfig("Total", nombreComlumns[5], 100, false);


        NumberField num_field1 = new NumberField();
        num_field1.setAllowBlank(false);
        num_field1.setAllowNegative(false);
        num_field1.setSelectOnFocus(true);

        SimpleStore cbStore = new SimpleStore("Tallas", new String[]{
                    "2",
                    "4",
                    "6",
                    "8", "10", "12", "14",
                    "XS",
                    "S",
                    "M",
                    "L",
                    "XL",
                    "XXL", "XXXL", "ESPECIALES", "UNICA"
                });
        cbStore.load();

        final ComboBox cb = new ComboBox();
        cb.setDisplayField("Tallas");
        cb.setStore(cbStore);

        tallaColumn.setEditor(new GridEditor(cb));
        // num_field1.setMaxValue(1000);

        NumberField num_field2 = new NumberField();
        num_field2.setAllowBlank(false);
        num_field2.setAllowNegative(false);
        num_field2.setSelectOnFocus(true);
        //num_field2.setMaxValue(1000);


//        precioBsColumn.setEditor(new GridEditor(num_field1));
        cantidadColumn.setEditor(new GridEditor(num_field1));
        punitColumn.setEditor(new GridEditor(num_field2));


        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    detalleColumn,
                    tallaColumn,
                    cantidadColumn,
                    punitColumn,
                    totalColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        //grid.setId("grid-lista-productosproveedor");
        grid.setWidth(750);
        grid.setHeight(300);
        grid.setTitle("Lista");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(false);
        grid.setLoadMask(false);
        grid.setSelectionModel(new RowSelectionModel());
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(false);
        grid.setIconCls("grid-icon");
        grid.setAutoScroll(true);
        grid.setClicksToEdit(1);
        grid.setView(new GridView() {

            @Override
            public String getRowClass(Record record, int index, RowParams rowParams, Store store) {
                String estado = record.getAsString("estado");
                if (estado.equals("anulado") == true) {
                    return "RED";

                }
                if (estado.equals("pendiente") == true) {
                    return "YELLOW";
                } else {
                    return "NONE";
                }

            }
        });

        eliminarProducto = new ToolbarButton("Quitar");
        eliminarProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Quitar producto(s)");
        //tipsConfig.setTitle("Tip Title");
        eliminarProducto.setTooltip(tipsConfig2);

        detalleProducto = new ToolbarButton("Detalle");
        detalleProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Quitar producto(s)");
        //tipsConfig.setTitle("Tip Title");
        detalleProducto.setTooltip(tipsConfig1);

        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(false);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarProducto);
        pagingToolbar.addButton(detalleProducto);

        grid.setBottomToolbar(pagingToolbar);

//        aniadirListenersBuscador();
//
//        aniadirListenersBuscadoresText();
        panel1.add(tex_detalle);
        panel1.add(com_telas);
        panel1.add(com_colores);
        panel1.add(txa_detalle);
        panel1.add(txa_detallebordado);
        panel1.add(txa_detallecostura);
        panel1.add(com_estado);
        panel1.addButton(but_guardar);
        panel1.addButton(but_duplicar);
        initCombos();
        aniadirListenersProducto();
        panel.setLayout(new TableLayout(2));
        panel.setPaddings(5, 5, 5, 5);
        panel.add(new PaddedPanel(grid, 0, 0, 5, 0));
        panel.add(new PaddedPanel(panel1, 0, 0, 5, 0));
    }

    private void initCombos() {

        com_telas.setValueField("id");
        com_telas.setDisplayField("detalle");
        com_telas.setForceSelection(true);
        com_telas.setMinChars(1);
        com_telas.setMode(ComboBox.LOCAL);
        com_telas.setTriggerAction(ComboBox.ALL);
        com_telas.setEmptyText("Seleccione una Tela");
        com_telas.setLoadingText("Buscando");
        com_telas.setTypeAhead(true);
        com_telas.setSelectOnFocus(true);
        com_telas.setHideTrigger(true);
        com_telas.setReadOnly(false);
        SimpleStore telaStore = new SimpleStore(new String[]{"id", "detalle"}, telas);
        telaStore.load();
        com_telas.setStore(telaStore);



        com_colores.setValueField("id");
        com_colores.setDisplayField("nombre");
        com_colores.setForceSelection(true);
        com_colores.setMinChars(1);
        com_colores.setMode(ComboBox.LOCAL);
        com_colores.setTriggerAction(ComboBox.ALL);
        com_colores.setEmptyText("Seleccione un Color");
        com_colores.setLoadingText("Buscando");
        com_colores.setTypeAhead(true);
        com_colores.setSelectOnFocus(true);
        com_colores.setHideTrigger(true);
        com_colores.setReadOnly(false);
        SimpleStore marcaStore = new SimpleStore(new String[]{"id", "nombre"}, colores);
        marcaStore.load();
        com_colores.setStore(marcaStore);






        estados = new String[]{"completado", "pendiente", "anulado"};
        SimpleStore estadosStore = new SimpleStore("estado", estados);
        com_estado.setEmptyText("Seleccione un estado");
        estadosStore.load();
        com_estado.setDisplayField("estado");
        com_estado.setStore(estadosStore);
        com_estado.setValue("completado");


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
                    // MessageBox.alert("No hay producto selecionado para eliminar y/o selecciono mas de uno."+selecionado);
                    grid.stopEditing();
                    store.remove(cbSelectionModel.getSelected());
                    //
                    String enlace = "php/OrdenProduccion.php?funcion=EliminarOrdenProduccionItem&idordenDetalle=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el Compra", "cargar");
                                final Conector conec = new Conector(enlace, false);
                                try {
                                    conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {

                                        public void onResponseReceived(Request request, Response response) {
                                            String data = response.getText();
                                            JSONValue jsonValue = JSONParser.parse(data);
                                            JSONObject jsonObject;
                                            if ((jsonObject = jsonValue.isObject()) != null) {
                                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                                                if (errorR.equalsIgnoreCase("true")) {
                                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                                    //reload();
                                                } else {
                                                    Utils.setErrorPrincipal(mensajeR, "error");
                                                }
                                            }
                                        }

                                        public void onError(Request request, Throwable exception) {
                                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                        }


                                    });
                                } catch (RequestException ex) {
                                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                }
                    //
                    grid.startEditing(0, 0);
                } else {
                    MessageBox.alert("No hay producto selecionado para eliminar y/o selecciono mas de uno.");
                }
                eliminarProducto.setPressed(false);
            }
        });
        detalleProducto.addListener(new ButtonListenerAdapter() {

            private boolean procederAEliminar;
            int repeat = 0;

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("id");
                    grid.stopEditing();
                    MessageBox.alert("aqui debe entrar el reporte o detalle del producto");
                    grid.startEditing(0, 0);
                } else {
                    MessageBox.alert("No hay producto selecionado para eliminar y/o selecciono mas de uno.");
                }
                detalleProducto.setPressed(false);
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
                            MessageBox.alert("hola");
                        }

                    }
                });
//        grid.addGridMouseListener(new GridMouseListenerAdapter()instanceof
        grid.addGridRowListener(new GridRowListenerAdapter() {

            private String detallecostura;

            @Override
            public void onRowDblClick(GridPanel grid1, int rowIndex, EventObject e) {
                Record a = grid.getSelectionModel().getSelected();

//                 selecionado = new String("");
                selecionado = a.getAsString("detalle");
                tela = a.getAsString("tela");

                color = a.getAsString("color");
                detalle = a.getAsString("detalle1");
                detallebordado = a.getAsString("detallebordado");
                detallecostura = a.getAsString("detallecostura");
                estado = a.getAsString("estado");
                tex_detalle.setValue(selecionado);
                com_telas.setValue(tela);
                com_colores.setValue(color);
                com_estado.setValue(estado);
                txa_detalle.setValue(detalle);
                txa_detallebordado.setValue(detallebordado);
                txa_detallecostura.setValue(detallecostura);
                com_telas.focus();

            }
        });
        com_telas.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String tela = com_telas.getValueAsString().trim();
                    grid.getSelectionModel().getSelected().set("tela", tela);
                    com_colores.focus();

                }
            }
        });
        com_colores.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String tela = com_colores.getValueAsString().trim();
                    grid.getSelectionModel().getSelected().set("color", tela);
                    txa_detalle.focus();

                }
            }
        });
        txa_detalle.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String tela = txa_detalle.getValueAsString().trim();
                    grid.getSelectionModel().getSelected().set("detalle1", tela);
                    txa_detallebordado.focus();

                }
            }
        });
        txa_detallebordado.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String tela = txa_detallebordado.getValueAsString().trim();
                    grid.getSelectionModel().getSelected().set("detallebordado", tela);
                    txa_detallecostura.focus();

                }
            }
        });
        txa_detallecostura.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String tela = txa_detallecostura.getValueAsString().trim();
                    grid.getSelectionModel().getSelected().set("detallecostura", tela);
                    com_estado.focus();

                }
            }
        });
        com_estado.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String tela = com_estado.getValueAsString().trim();
                    grid.getSelectionModel().getSelected().set("estado", tela);
//                    com_estado.focus();

                }
            }
        });
        but_guardar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                String tela = com_telas.getValueAsString().trim();
                grid.getSelectionModel().getSelected().set("tela", tela);
                String tela1 = com_estado.getValueAsString().trim();
                grid.getSelectionModel().getSelected().set("estado", tela1);
                String tela2 = txa_detallebordado.getValueAsString().trim();
                grid.getSelectionModel().getSelected().set("detallebordado", tela2);
                String tela21 = txa_detallecostura.getValueAsString().trim();
                grid.getSelectionModel().getSelected().set("detallecostura", tela21);
                String tela3 = com_colores.getValueAsString().trim();
                grid.getSelectionModel().getSelected().set("color", tela3);
                String tela4 = txa_detalle.getValueAsString().trim();
                grid.getSelectionModel().getSelected().set("detalle1", tela4);

                MessageBox.alert("Guardado los Datos en ese Item");
            }
        });
        but_duplicar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {


//                    lista.getGrid().stopEditing();
//                    lista.getGrid().getStore().insert(0, registroCompra);
//                    lista.getGrid().startEditing(0, 0);
                Record a = grid.getSelectionModel().getSelected().copy();
//                 grid.getStore().cl
                grid.stopEditing();
                grid.getStore().insert(0, a);
                grid.startEditing(0, 0);
                MessageBox.alert("Duplicar los Datos en este Item");

            }
        });

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

    public void LimpiarPanelAdicioanl() {
        tex_detalle.setValue("");
        com_telas.clearValue();
        com_colores.clearValue();
        com_estado.clearValue();
        txa_detalle.setValue("");
        txa_detallebordado.setValue("");
        txa_detallecostura.setValue("");

    }

    public Store getStore() {
        return store;
    }

    public Record[] getRecords() {
        Record[] records = cbSelectionModel.getSelections();
        return records;
    }

    public ColumnModel getColumnModel() {
        return columnModel;
    }

    public CheckboxSelectionModel getSelectionModel() {
        return cbSelectionModel;
    }
}