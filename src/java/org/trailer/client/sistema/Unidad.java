/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.ScriptTagProxy;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
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
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;

/**
 *
 * @author miguel
 */
public class Unidad extends Panel {

    private GridPanel grid;
    private ColumnConfig nombreColumn;
    private ColumnConfig codigoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton nuevoUnidad;
    private ToolbarButton editarUnidad;
    private ToolbarButton eliminarUnidad;
    private ToolbarButton actualizarUnidad;
    private EditarUnidadForm formulario;
    protected ExtElement ext_element;
    private JsonReader reader;
    private CheckboxSelectionModel cbSelectionModel;
    private Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    private ColumnModel columnModel;
    private ColumnConfig idUnidadColumn;
    private ColumnConfig codigoUnidadColumn;
    private ColumnConfig nombreUnidadColumn;
    private ColumnConfig idColumn;

    public Unidad() {
        this.setClosable(true);
        this.setId("TPfun1010");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Unidad");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Unidad.php?funcion=ListarUnidad");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idunidad"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("codigo"),});
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);

        /* columnade idusuario  */

        /* columnade nombre  */
        idColumn = new ColumnConfig("Id Unidad", "idunidad", (ANCHO / 7) - 50, true);
//        idColumn.setId("expandible");
        codigoUnidadColumn = new ColumnConfig("Codigo", "codigo", 200, true);
        nombreUnidadColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 2), true);
        nombreUnidadColumn.setId("expandible");



        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),

                    codigoUnidadColumn,
                    nombreUnidadColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Unidad");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de Unidades");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(true);
        grid.setAutoExpandColumn("expandible");
        grid.setLoadMask(true);
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setIconCls("grid-icon");
        grid.addGridRowListener(new GridRowListenerAdapter() {

            @Override
            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");

            }
        });

//        grid.a


        nuevoUnidad = new ToolbarButton("Nuevo");
        nuevoUnidad.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear nuevo Unidad");
        nuevoUnidad.setTooltip(tipsConfig1);

        editarUnidad = new ToolbarButton("Editar");
        editarUnidad.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Unidad");
        editarUnidad.setTooltip(tipsConfig);

        eliminarUnidad = new ToolbarButton("Eliminar");
        eliminarUnidad.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Unidad");
        eliminarUnidad.setTooltip(tipsConfig2);




        PagingToolbar pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoUnidad);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarUnidad);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarUnidad);
        pagingToolbar.addSeparator();

        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);
        aniadirListenersUnidad();

    //RootPanel.get().add(panel);
    }

    public GridPanel getGrid() {
        return grid;
    }

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    public void setGrid(GridPanel grid) {
        this.grid = grid;
    }


    private void cargarDatosNuevoUnidad() {

        formulario = null;
        formulario = new EditarUnidadForm(null, "", "", Unidad.this);
        formulario.show();

    }
    //**************************************************
    //***********EDITAR ROL
    //**************************************************

    private void cargarDatosEditarUnidad(String idunidad) {
        String enlace = "php/Unidad.php?funcion=BuscarUnidadPorId&idunidad=" + idunidad;
        Utils.setErrorPrincipal("Cargando parametros para Unidad", "cargar");
        final Conector conec = new Conector(enlace, false);

        try {
            conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                public void onResponseReceived(Request request, Response response) {
                    String data = response.getText();
                    JSONValue jsonValue = JSONParser.parse(data);
                    JSONObject jsonObject;
                    if ((jsonObject = jsonValue.isObject()) != null) {
                        String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                        String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                        if (errorR.equalsIgnoreCase("true")) {
                            Utils.setErrorPrincipal(mensajeR, "mensaje");

                            JSONValue productoV = jsonObject.get("resultado");
                            JSONObject productoO;
                            if ((productoO = productoV.isObject()) != null) {
                                String idunidad = Utils.getStringOfJSONObject(productoO, "idunidad");
                                String codigo = Utils.getStringOfJSONObject(productoO, "codigo");
                                String nombre = Utils.getStringOfJSONObject(productoO, "nombre");


                                formulario = null;
                                formulario = new EditarUnidadForm(idunidad,codigo,nombre,Unidad.this);
                                formulario.show();
                            } else {
                                Utils.setErrorPrincipal("Error en el objeto unidad", "error");
                            }
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
            ex.getMessage();
            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
        }

    }

    private void aniadirListenersUnidad() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarUnidad.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idunidad");
                    MessageBox.confirm("Eliminar...", "Realmente desea eliminar??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Unidad.php?funcion=EliminarUnidad&idunidad=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando...", "cargar");
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
                                                    reload();
                                                } else {
                                                    //Window.alert(mensajeR);
                                                    Utils.setErrorPrincipal(mensajeR, "error");
                                                }
                                            }
                                        }

                                        public void onError(Request request, Throwable exception) {
                                            //Window.alert("Ocurrio un error al conectar con el servidor ");
                                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                        }
                                    });
                                } catch (RequestException ex) {
                                    //Window.alert("Ocurrio un error al conectar con el servidor");
                                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                }
                            }
                        }
                    });
                } else {
                    MessageBox.alert("No hay venta selecionado para editar y/o selecciono mas de uno.");
                }
                eliminarUnidad.setPressed(false);
            }
        });
        nuevoUnidad.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                cargarDatosNuevoUnidad();
            }
        });
        editarUnidad.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    String idunidad = records[0].getAsString("idunidad");
                    cargarDatosEditarUnidad(idunidad);
                } else {
                    Utils.setErrorPrincipal("Por favor seleccione una unidad para editar", "error");
                }

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

    }
}

