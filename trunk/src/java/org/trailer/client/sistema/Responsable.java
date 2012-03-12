/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import org.trailer.client.util.BuscadorToolBar;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.data.*;
import com.gwtext.client.widgets.form.Checkbox;

/**
 *
 * @author buggy
 */
public class Responsable extends Panel {

    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig ciudadColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig telefonoColumn;
    private ColumnConfig direccionColumn;
    private ColumnConfig empresaColumn;
    private ColumnConfig apellidoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarResponsable;
    private ToolbarButton eliminarResponsable;
    private ToolbarButton nuevoResponsable;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarcodigo;
    protected String buscarnombre;
    protected String buscarempresa;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
    private EditarResponsableForm formulario;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public Responsable() {
        this.setClosable(true);
        this.setId("TPfun1008");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Responsables");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Responsable.php?funcion=ListarResponsables");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idresponsable"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("apellido1"),
                    new StringFieldDef("empresa"),
                    new StringFieldDef("ciudad"),
                    new StringFieldDef("telefono"),
                    new StringFieldDef("direccion")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Responsable", "idresponsable", (ANCHO / 7), true);
        idColumn.setWidth(100);
        codigoColumn = new ColumnConfig("Codigo", "codigo", (ANCHO / 7));

        codigoColumn.setId("expandible");
        ciudadColumn = new ColumnConfig("Ciudad", "ciudad", (ANCHO / 7), true);
        nombreColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 7), true);
        telefonoColumn = new ColumnConfig("Telefono", "telefono", (ANCHO / 7), true);
        direccionColumn = new ColumnConfig("Direccion", "direccion", (ANCHO / 7), true);
        //codigoColumn = new ColumnConfig("Codigo", "codigo", (ANCHO / 7), true);
        apellidoColumn = new ColumnConfig("Apellido", "apellido1", (ANCHO / 7), true);
        empresaColumn = new ColumnConfig("Empresa", "empresa", (ANCHO / 7), true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    codigoColumn,
                    nombreColumn,
                    apellidoColumn,
                    empresaColumn,
                    ciudadColumn,
                    telefonoColumn,
                    direccionColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Responsable");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de Responsables");
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
//                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");
                Record[] records = cbSelectionModel.getSelections();


                selecionado = records[0].getAsString("idresponsable");
                String enlTemp = "funcion=reporteClienteHTML&idresponsable=" + selecionado;
                verReporte(enlTemp);
//                    Window.alert(enlTemp);
            }
        });

        nuevoResponsable = new ToolbarButton("Nuevo");
        nuevoResponsable.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear Resonsables");
        nuevoResponsable.setTooltip(tipsConfig1);

        editarResponsable = new ToolbarButton("Editar");
        editarResponsable.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Responsable");
        editarResponsable.setTooltip(tipsConfig);

        eliminarResponsable = new ToolbarButton("Eliminar");
        eliminarResponsable.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Responsable");
        eliminarResponsable.setTooltip(tipsConfig2);



        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoResponsable);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarResponsable);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarResponsable);

        String items[] = {"Codigo", "Nombre", "Empresa"};
        String tiposItems[] = {"text", "text", "text"};
        buscadorToolBar = new BuscadorToolBar(items, tiposItems);
        grid.setTopToolbar(buscadorToolBar.getToolbar());
        grid.setBottomToolbar(pagingToolbar);
        buscar = buscadorToolBar.getBuscar();
//
        addListenersBuscador();

        addListenersBuscadoresText();


        add(grid);
        //panel.add(grid);
        aniadirListenersCliente();
    }

    public GridPanel getGrid() {
        return grid;
    }

    public void setGrid(GridPanel grid) {
        this.grid = grid;
    }

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    private void CargarNuevoCliente() {
        String enlace = "php/Responsable.php?funcion=CargarNuevoResponsable";
        Utils.setErrorPrincipal("Cargando parametros de Nuevo Responsable", "cargar");
        final Conector conec = new Conector(enlace, false);
        {
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

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {
                                    Object[][] empresa = Utils.getArrayOfJSONObject(marcaO, "empresaM", new String[]{"idempresa", "nombre"});
                                    Object[][] ciudades = Utils.getArrayOfJSONObject(marcaO, "ciudadM", new String[]{"idciudad", "nombre"});

                                    formulario = null;
                                    formulario = new EditarResponsableForm(null, "", "", "", "", "", "", "", "", empresa, "", ciudades, "", "", Responsable.this);
                                    formulario.show();
                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        } else {
                        }
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });

            } catch (RequestException e) {
                e.getMessage();
                MessageBox.alert("Ocurrio un error al conectarse con el SERVIDOR");
            }

        }
    }

    private void cargarDatosEditarCliente(String idresponsable) {
        String enlace = "php/Responsable.php?funcion=BuscarResponsablePorId&idresponsable=" + idresponsable;
        Utils.setErrorPrincipal("Cargando parametros de nuevo Responsable", "cargar");
        final Conector conec = new Conector(enlace, false);
        {
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

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {
                                    Object[][] ciudades = Utils.getArrayOfJSONObject(marcaO, "ciudadM", new String[]{"idciudad", "nombre"});
                                    Object[][] empresas = Utils.getArrayOfJSONObject(marcaO, "empresaM", new String[]{"idempresa", "nombre"});
                                    String idresponsable = Utils.getStringOfJSONObject(marcaO, "idresponsable");
                                    String codigo = Utils.getStringOfJSONObject(marcaO, "codigo");
                                    String ciudad = Utils.getStringOfJSONObject(marcaO, "idciudad");
                                    String empresa = Utils.getStringOfJSONObject(marcaO, "idempresa");
                                    String nombre = Utils.getStringOfJSONObject(marcaO, "nombre");
                                    String apellido = Utils.getStringOfJSONObject(marcaO, "apellido");
                                    String telefono = Utils.getStringOfJSONObject(marcaO, "telefono");
                                    String direccion = Utils.getStringOfJSONObject(marcaO, "direccion");
                                    String email = Utils.getStringOfJSONObject(marcaO, "email");
                                    String observacion = Utils.getStringOfJSONObject(marcaO, "observacion");
                                    String fecha = Utils.getStringOfJSONObject(marcaO, "fecha");
//                                    String  = Utils.getStringOfJSONObject(marcaO, "estado");
                                    formulario = null;
                                    formulario = new EditarResponsableForm(idresponsable, nombre, apellido, codigo, ciudad, telefono, email, direccion, observacion, empresas, empresa, ciudades, ciudad, fecha, Responsable.this);
                                    formulario.show();
                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        } else {
                        }
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });

            } catch (RequestException e) {
                e.getMessage();
                MessageBox.alert("Ocurrio un error al conectarse con el SERVIDOR");
            }

        }
    }

    private void aniadirListenersCliente() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarResponsable.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idresponsable");
                    MessageBox.confirm("Eliminar Responsable", "Realmente desea eliminar este Responsable??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Responsable.php?funcion=EliminarResponsable&idresponsable=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el Responsable", "cargar");
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
                    MessageBox.alert("No hay Cliente selecionado para el Cliente y/o selecciono mas de uno.");
                }
                eliminarResponsable.setPressed(false);
            }
        });
        //**************************************************
        //***********NUEVO CLIENTE
        //**
        nuevoResponsable.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                CargarNuevoCliente();
            }
        });

        //**************************************************
        //***********EDITAR CLIENTE
        //**************************************************
        editarResponsable.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idcliente = records[0].getAsString("idresponsable");
                            cargarDatosEditarCliente(idcliente);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un Responsable para editar", "error");
                        }

                    }
                });
//        reporteCliente.addListener(
//                new ButtonListenerAdapter() {
//
//                    @Override
//                    public void onClick(Button button, EventObject e) {
//                        Record[] records = cbSelectionModel.getSelections();
//
//                        if (records.length == 1) {
//                            selecionado = records[0].getAsString("idcliente");
//                            String enlTemp = "funcion=reporteclienteHTML&idcliente=" + selecionado;
//                            verReporte(enlTemp);
//
//                        } else {
//                            MessageBox.alert("No hay marca selecionado para ver el reporte.");
//                        }
//
//                        reporteCliente.setPressed(false);
//                    }
//                });
//        cambiarestado.addListener(
//                new ButtonListenerAdapter() {
//
//                    @Override
//                    public void onClick(Button button, EventObject e) {
//                        Record[] records = cbSelectionModel.getSelections();
//                        if (records.length == 1) {
//                            String idcliente = records[0].getAsString("idcliente");
//
//                        } else {
//
//                            Utils.setErrorPrincipal("seleccione un cliente para cambiar estado", "error");
//                        }
//
//                    }
//                });



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

    public String getLinksaveUpdate(String idrol, String nombre, String estado, Object[] seleccionados) {
        String dev = "";
        if (seleccionados.length >= 1) {

            if (idrol == null) {
                idrol = "nuevo";
            }
            JSONObject todos = new JSONObject();
            todos.put("idrol", new JSONString(idrol));
            todos.put("nombre", new JSONString(nombre));
            todos.put("estado", new JSONString(estado));
            JSONArray funcS = new JSONArray();
            for (int i = 0; i < seleccionados.length; i++) {
                Checkbox che = (Checkbox) seleccionados[i];
                funcS.set(i, new JSONString(che.getId().substring(1)));
            }
            todos.put("funciones", funcS);
            dev = todos.toString();
        } else {
            Utils.setErrorPrincipal("Por favor seleccione por lo menos una funacion", "error");
        }
        return dev;
    }

    private void addListenersBuscador() {
        buscar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                buscarSegunParametros();
            }
        });
    }

    private void addListenersBuscadoresText() {

        //*********************************************************************
        //***************BUSCADOR NIT************************************
        //*********************************************************************
        buscadorToolBar.getItemsText1().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //*********************************************************************
        //***************BUSCADOR DE NOMBRE************************************
        //*********************************************************************
        buscadorToolBar.getItemsText2().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //*********************************************************************
        //***************BUSCADOR DE APELLIDO************************************
        //*********************************************************************
        buscadorToolBar.getItemsText3().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

    }

    public void buscarSegunParametros() {
        buscarcodigo = buscadorToolBar.getItemsText1().getText();
        buscarnombre = buscadorToolBar.getItemsText2().getText();
        buscarempresa = buscadorToolBar.getItemsText3().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarcodigo", buscarcodigo),
                    new UrlParam("buscarnombre", buscarnombre),
                    new UrlParam("buscarempresa", buscarempresa)
                }, false);
    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
