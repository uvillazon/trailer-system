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
//import org.trailer.client.marca.EditarMarcaForm;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
//import org.selkis.client.util.Utils;

/**
 *
 * @author Administrador
 */
public class CuentaPrincipal extends Panel {

    private GridPanel grid;
    private ColumnConfig nombreColumn;
    //private ColumnConfig estadoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton nuevaCuenta;
    private ToolbarButton editarCuenta;
    private ToolbarButton eliminarCuenta;
//    private ToolbarButton ;
    private EditarCuentaPrincipalForm formulario;
    protected ExtElement ext_element;
    private JsonReader reader;
    private CheckboxSelectionModel cbSelectionModel;
    private Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    private ColumnModel columnModel;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig nombColumn;
    private ColumnConfig tipoColumn;
    // private ColumnConfig tipoEmColumn;
    private ColumnConfig celularColumn;

    public CuentaPrincipal() {
        this.setClosable(true);
        this.setId("TPfun1014");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Cuentas Principales");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/CuentaPrincipal.php?funcion=ListarCuentasPrincipales");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idcuentaprincipal"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("tipo"),});
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);

        /* columnade idusuario  */

        /* columnade nombre  */
        idColumn = new ColumnConfig("Id Cuenta", "idcuentaprincipal", (ANCHO / 4), true);

        codigoColumn = new ColumnConfig("Abreviacion", "codigo", (ANCHO / 4), true);
        codigoColumn.setId("expandible");
        nombColumn = new ColumnConfig("Nombres", "nombre", (ANCHO / 4), true);
        tipoColumn = new ColumnConfig("Tipo", "tipo", (ANCHO / 4), true);




        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    //column ID is company which is later used in setAutoExpandColumn
                    //                                        idColumn,
                    //                    idColumn,
                    codigoColumn,
                    nombColumn,
                    tipoColumn
//                    estadoColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-cuentas-principales");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de Cuentas");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(true);
        grid.setAutoExpandColumn("expandible");
        grid.setLoadMask(true);
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setIconCls("grid-icon");


//        grid.a


        nuevaCuenta = new ToolbarButton("Nuevo");

        nuevaCuenta.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();

        tipsConfig1.setText("Crear nueva Cuenta Principal");

        nuevaCuenta.setTooltip(tipsConfig1);

        editarCuenta = new ToolbarButton("Editar");
        editarCuenta.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Datos de Cuenta Principal");
        editarCuenta.setTooltip(tipsConfig);


        eliminarCuenta = new ToolbarButton("Eliminar");
        eliminarCuenta.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Cuenta Principal");
        eliminarCuenta.setTooltip(tipsConfig2);


        PagingToolbar pagingToolbar = new PagingToolbar(store);

        pagingToolbar.setPageSize(100);

        pagingToolbar.setDisplayInfo(true);

        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");

        pagingToolbar.setEmptyMsg("No topics to display");

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(nuevaCuenta);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(editarCuenta);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(eliminarCuenta);


        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);

        aniadirListenersProveedor();

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

    private void cargarDatosNuevoProceso() {

        formulario = null;
//                                    MessageBox.alert("Envio parametros");
//        formulario = new EditarProcesoForm(null, "", "", "", "", "", "", encargadoM, "", CuentaPrincipal.this, "");
        formulario = new EditarCuentaPrincipalForm(null, "", "", "", this);
        formulario.show();

    }

    private void aniadirListenersProveedor() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarCuenta.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idcuentaprincipal");
                    MessageBox.confirm("Eliminar Cuenta Principal", "Realmente desea eliminar esta Cuenta??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/CuentaPrincipal.php?funcion=EliminarCuentaPrincipal&idcuenta=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el Proceso", "cargar");
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
                eliminarCuenta.setPressed(false);
            }
        });
        nuevaCuenta.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                cargarDatosNuevoProceso();
            }
        });
        editarCuenta.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    String idcuenta = records[0].getAsString("idcuentaprincipal");
                    String codigo = records[0].getAsString("codigo");
                    String nombre = records[0].getAsString("nombre");
                    String tipo = records[0].getAsString("tipo");
                    formulario = null;

                    formulario = new EditarCuentaPrincipalForm(idcuenta, codigo, nombre, tipo, CuentaPrincipal.this);
                    formulario.show();
                } else {
                    Utils.setErrorPrincipal("Por favor seleccione un producto para editar", "error");
                }

            }
        });



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

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}

