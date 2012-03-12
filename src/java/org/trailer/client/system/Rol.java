/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.system;

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
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import org.trailer.client.util.Utils;

/**
 *
 * @author Administrador
 */
public class Rol extends Panel {

    private GridPanel grid;
    private ColumnConfig nombreColumn;
    private ColumnConfig estadoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarRol;
    private ToolbarButton eliminarRol;
    private ToolbarButton nuevoRol;
    private ToolbarButton duplicarRol;
    protected EditarRolForm formulario;
    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public Rol() {
        this.setClosable(true);
        this.setId("TPfun1007");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Roles");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Rol.php?funcion=listarRol");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idrol"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("estado")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);

        /* columnade idusuario  */

        /* columnade nombre  */
        nombreColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 2) - 50, true);
        nombreColumn.setId("expandible");
        estadoColumn = new ColumnConfig("Estado", "estado", (ANCHO / 2) - 50, true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    //column ID is company which is later used in setAutoExpandColumn
                    nombreColumn,
                    estadoColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-roles");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de roles");
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


        nuevoRol = new ToolbarButton("Nuevo");
        nuevoRol.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear nuevo rol");
        nuevoRol.setTooltip(tipsConfig1);

        editarRol = new ToolbarButton("Editar");
        editarRol.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar rol");
        editarRol.setTooltip(tipsConfig);

        eliminarRol = new ToolbarButton("Eliminar");
        eliminarRol.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar rol(es)");
        eliminarRol.setTooltip(tipsConfig2);

        duplicarRol = new ToolbarButton("Duplicar");
        duplicarRol.setEnableToggle(true);
        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        tipsConfig3.setText("Duplicar Rol");
        duplicarRol.setTooltip(tipsConfig);


        PagingToolbar pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoRol);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarRol);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarRol);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(duplicarRol);
        pagingToolbar.addSeparator();

        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);
        aniadirListenersRoles();

    //RootPanel.get().add(panel);
    }

    public GridPanel getGrid() {
        return grid;
    }

    public void setGrid(GridPanel grid) {
        this.grid = grid;
    }

    private void aniadirListenersFormulario() {

        formulario.getBut_aceptar().addListener(new ButtonListenerAdapter() {

            int repeat = 0;

            @Override
            public void onClick(Button button, EventObject e) {
                String idrol = formulario.getIdRol();
                String nombre = formulario.getTex_nombre().getText();
                String estado = formulario.getCom_estado().getText();
                //com.google.gwt.user.client.Window.alert(idrol + "-->" + nombre + "-->" + estado);
                String enlace = "";
                if (idrol != null) {
                    //com.google.gwt.user.client.Window.alert(formulario.getSeleccionados() + "<-----> es para modificsr");
                    enlace = getLinksaveUpdate(idrol, nombre, estado, formulario.getSeleccionados());
                    enlace = "php/Rol.php?funcion=txSaveUpdateRol&resultado=" + enlace;

                } else {
                    enlace = getLinksaveUpdate("nuevo", nombre, estado, formulario.getSeleccionados());
                    enlace = "php/Rol.php?funcion=txSaveUpdateRol&resultado=" + enlace;
                }
                Conector cones = new Conector(enlace, false);
                Utils.setErrorPrincipal("Guardando los cambios en el rol", "cargar");
                try {
                    cones.getRequestBuilder().sendRequest(null, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String dataGU = response.getText();
                            JSONValue jsonValue = JSONParser.parse(dataGU);
                            JSONObject jsonObjectGU;
                            if ((jsonObjectGU = jsonValue.isObject()) != null) {
                                String errorR = Utils.getStringOfJSONObject(jsonObjectGU, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObjectGU, "mensaje");
                                if (errorR.equalsIgnoreCase("True")) {
                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                    formulario.getWindow().close();
                                    formulario = null;
                                    store.reload();
                                    grid.reconfigure(store, columnModel);
                                    grid.getView().refresh();
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            }
                        }

                        public void onError(Request request, Throwable exception) {
                            Utils.setErrorPrincipal("No se pudo conectar con el servidor", "error");
                        }
                    });
                } catch (RequestException ex) {
                    Utils.setErrorPrincipal("No se pudo conectar con el servidor", "error");
                }
            }
        });

        formulario.getBut_cancelar().addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                formulario.getWindow().close();
                formulario.getWindow().destroy();
                formulario = null;

            }
        });
    }

    private void aniadirListenersRoles() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarRol.addListener(new ButtonListenerAdapter() {

            int repeat = 0;

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length >= 1) {
                    MessageBox.confirm("Eliminar rol", "Realmente desea eliminar el o los roles ??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                Record[] recordss = cbSelectionModel.getSelections();
                                JSONArray funcS = new JSONArray();
                                for (int i = 0; i < recordss.length; i++) {

                                    funcS.set(i, new JSONString(recordss[i].getAsString("idrol")));
                                }
                                JSONObject joo = new JSONObject();
                                joo.put("resultado", funcS);
                                String enlace = "php/Rol.php?funcion=txDelete&resultado=" + joo.toString();
                                Utils.setErrorPrincipal("Eliminando los roles seleccionados", "cargar");
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

                                                    store.reload();
                                                    grid.reconfigure(store, columnModel);
                                                    grid.getView().refresh();
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

                            }
                        }
                    });
                } else {
                    MessageBox.alert("No hay rol selecionado para eliminar");
                }
                eliminarRol.setPressed(false);
            }
        });

        //**************************************************
        //***********EDITAR ROL
        //**************************************************
        editarRol.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idrol");
                            String enlace = "php/Rol.php?funcion=findAllRolWithFunctions&idrol=" + selecionado;
                            Utils.setErrorPrincipal("Cargando parametros para editar rol", "cargar");
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
                                                if (formulario != null) {
                                                    formulario.getWindow().clear();
                                                    formulario.getWindow().destroy();
                                                    formulario = null;
                                                }
                                                formulario = new EditarRolForm(selecionado, jsonObject);
                                                formulario.getWindow().show();
                                                aniadirListenersFormulario();
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
                        } else {
                            MessageBox.alert("No hay rol selecionado para editar.");
                        }
                        editarRol.setPressed(false);
                    }
                });

        //**************************************************
        //***********NUEVO USUARIO
        //**************************************************
        nuevoRol.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        String enlace = "php/Rol.php?funcion=findAllRolWithFunctions";
                        Utils.setErrorPrincipal("Cargando parametros para crear nuevo rol", "cargar");
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
                                            if (formulario != null) {
                                                formulario.clear();
                                                formulario.destroy();
                                                formulario = null;
                                            }
                                            formulario = new EditarRolForm(null, jsonObject);
                                            formulario.getWindow().show();
                                            aniadirListenersFormulario();
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
                    }
                });
        duplicarRol.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            MessageBox.prompt("NombreRol", "Ingrese el nombre para el nuevo rol:",
                                    new MessageBox.PromptCallback() {

                                        public void execute(String btnID, String text) {
                                            if (btnID.equalsIgnoreCase("ok")) {
                                                Record[] recordss = cbSelectionModel.getSelections();
                                                String selecionado = recordss[0].getAsString("idrol");
                                                String enlace = "php/Rol.php?funcion=txDuplicate&idrol=" + selecionado + "&nombre=" + text;
                                                Utils.setErrorPrincipal("Guardando una replica del rol", "cargar");
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
                                                                    store.reload();
                                                                    grid.reconfigure(store, columnModel);
                                                                    grid.getView().refresh();
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
                                            }
                                        }
                                    });
                        } else {
                            if (records.length > 1) {
                                Utils.setErrorPrincipal("Usted selecciono mas de un rol", "error");
                            } else {
                                Utils.setErrorPrincipal("Usted no selecciono ningun rol", "error");
                            }

                        }
                        duplicarRol.setPressed(false);



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
}
