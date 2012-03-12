/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.parametros;

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
import org.trailer.client.MainEntryPoint;
import org.trailer.client.util.KMenu;

/**
 *
 * @author Uvillazon
 */
public class Grupos extends Panel {

    private KMenu kmenu;
    private MainEntryPoint pan;
    private GridPanel grid;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton nuevoGrupo;
    private ToolbarButton editarGrupo;
    private ToolbarButton eliminarGrupo;
    private ToolbarButton componentes;
    private JsonReader reader;
    private CheckboxSelectionModel cbSelectionModel;
    private Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    private ColumnModel columnModel;
    private ColumnConfig idGrupoColumn;
    private ColumnConfig codigoGrupoColumn;
    private ColumnConfig nombreGrupoColumn;
    private ColumnConfig descripcionGrupoColumn;
    private EditarGrupoForm formulario;
    private Componentes formC;

    public Grupos(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun2004");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Grupos");
        onModuleLoad();
    }

    private void onModuleLoad() {
        DataProxy dataProxy = new ScriptTagProxy("php/Grupo.php?funcion=ListarGrupos");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idgrupo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("descripcion"),});
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);

        idGrupoColumn = new ColumnConfig("Id grupo", "idgrupo", 200, true);
        codigoGrupoColumn = new ColumnConfig("Codigo", "codigo", 200, true);
        nombreGrupoColumn = new ColumnConfig("Nombre", "nombre", 500, true);
        //nombreGrupoColumn.setId("expandible");
        descripcionGrupoColumn = new ColumnConfig("Descripcion", "descripcion", 200, true);
        descripcionGrupoColumn.setId("expandible");
        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    nombreGrupoColumn,
                    descripcionGrupoColumn
                };
        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Grupo");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de Grupos");
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

            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");

            }
        });

//        grid.a


        nuevoGrupo = new ToolbarButton("Nuevo");
        nuevoGrupo.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear nuevo Grupo");
        nuevoGrupo.setTooltip(tipsConfig1);

        editarGrupo = new ToolbarButton("Editar");
        editarGrupo.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Grupo");
        editarGrupo.setTooltip(tipsConfig);

        eliminarGrupo = new ToolbarButton("Eliminar");
        eliminarGrupo.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Grupo");
        eliminarGrupo.setTooltip(tipsConfig2);


        componentes = new ToolbarButton("Componentes");
        componentes.setEnableToggle(true);
        QuickTipsConfig tipsConfig21 = new QuickTipsConfig();
        tipsConfig21.setText("Componentes");
        componentes.setTooltip(tipsConfig21);


        PagingToolbar pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoGrupo);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarGrupo);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarGrupo);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(componentes);

        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);
        aniadirListenersGrupo();


    }

    private void cargarDatosComponentes(final String idgrupo, final String nombre, final String descripcion) {
        String enlace = "php/Componentes.php?funcion=BuscarComponentePorId&idgrupo=" + idgrupo;
        Utils.setErrorPrincipal("Cargando parametros de la compra", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    private EventObject e;

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
                                    Object[][] procesos = Utils.getArrayOfJSONObject(marcaO, "procesoM", new String[]{"id", "costo", "unidad", "detalle"});
                                    Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoM", new String[]{"id", "costo", "unidad", "detalle"});
                                    Object[][] material = Utils.getArrayOfJSONObject(marcaO, "materiaM", new String[]{"id", "costo", "unidad", "detalle"});
//                                    Object[][] compra = Utils.getArrayOfJSONObject(marcaO, "compra", new String[]{"idcompra", "numerodocumento","idproveedor","idempleado",
//                                    "fecha","tipodocumento","montototal","montoapagar","descuentoporcentaje","descuento","observacion"});
                                    Object[][] detalles = Utils.getArrayOfJSONObject(marcaO, "detalleM", new String[]{"id", "detalle", "unidad", "cantidad", "preciounitario","preciototal"});
                                  
//                                    MessageBox.alert("cargo los arreglos .");
                                    formC = null;
                                    formC = new Componentes(idgrupo, nombre, descripcion, null, null,procesos, productos, material, detalles ,"grupo");
                                    kmenu.seleccionarOpcion(null, "fun2005", e, formC);

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

    private void aniadirListenersGrupo() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarGrupo.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idgrupo");
                    MessageBox.confirm("Eliminar...", "Realmente desea eliminar??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Grupo.php?funcion=EliminarGrupo&idgrupo=" + selecionado;
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
                eliminarGrupo.setPressed(false);
            }
        });
        nuevoGrupo.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                cargarDatosNuevoGrupo();
            }
        });
        componentes.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idgrupo = records[0].getAsString("idgrupo");
                            String nombre = records[0].getAsString("nombre");
                            String descripcion = records[0].getAsString("descripcion");
                            cargarDatosComponentes(idgrupo, nombre, descripcion);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un Grupo", "error");
                        }

                    }
                });

        editarGrupo.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    String idcategoria = records[0].getAsString("idgrupo");
                    cargarDatosEditarCategoria(idcategoria);
                } else {
                    Utils.setErrorPrincipal("Por favor seleccione una categoria para editar", "error");
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

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    private void cargarDatosNuevoGrupo() {

        formulario = null;
        formulario = new EditarGrupoForm(null, "", "", Grupos.this);
        // formulario = new EditarGrupoForm("", "", "", Grupo.this);
        formulario.show();

    }
    //**************************************************
    //***********EDITAR ROL
    //**************************************************

    private void cargarDatosEditarCategoria(String idgrupo) {
        String enlace = "php/Grupo.php?funcion=BuscarGruposPorId&idgrupo=" + idgrupo;
        Utils.setErrorPrincipal("Cargando parametros para Categoria", "cargar");
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
                                String idgrupo = Utils.getStringOfJSONObject(productoO, "idgrupo");
                                String codigo = Utils.getStringOfJSONObject(productoO, "nombre");
                                String nombre = Utils.getStringOfJSONObject(productoO, "descripcion");


                                formulario = null;
                                formulario = new EditarGrupoForm(idgrupo, codigo, nombre, Grupos.this);
                                formulario.show();
                            } else {
                                Utils.setErrorPrincipal("Error en el objeto categoria", "error");
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
}
