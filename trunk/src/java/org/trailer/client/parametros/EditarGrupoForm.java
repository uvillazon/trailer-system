/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.parametros;

import org.trailer.client.sistema.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author buggy
 */
public class EditarGrupoForm extends Window {

    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_nombreGrupo;
    private TextArea tex_descripcionGrupo;
    //rivate TextField tex_codigoBarraColor;
    private Button but_aceptarGrupo;
    private Button but_cancelarGrupo;
    private String descripcionGrupo;
    private String idgrupo;
    private String nombreGrupo;
    private boolean nuevo;
    private Grupos padre;
//EditarGrupoForm(String string, String string0, String string1, Grupos aThis) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    public EditarGrupoForm(String idgrupo, String nombres, String descripcion, Grupos padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idgrupo = idgrupo;
        this.descripcionGrupo = nombres;
        this.nombreGrupo = descripcion;

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar Nuevo Grupo";

        if (idgrupo != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Grupo";
            nuevo = false;
        } else {
            this.idgrupo = "nuevo";
            nuevo = true;

        }

        setId("win-productos");
        but_aceptarGrupo = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarGrupo();
                } else {
                    GuardarNuevoGrupo();
                }
            }
        });
        but_cancelarGrupo = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarGrupoForm.this.close();
                EditarGrupoForm.this.setModal(false);
            //formulario = null;
            }
        });

        setTitle(tituloTabla);
        setAutoHeight(true);
        setAutoWidth(true);
        setLayout(new FitLayout());
        setPaddings(WINDOW_PADDING);
        setButtonAlign(Position.CENTER);
        addButton(but_aceptarGrupo);
        addButton(but_cancelarGrupo);

        setCloseAction(Window.CLOSE);
        setPlain(true);
        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setTitle("Registro De Categorias");

        formPanel = new FormPanel();




        tex_nombreGrupo = new TextField("Nombre", "nombre", 100);
        tex_nombreGrupo.setMaxLength(6);
        tex_descripcionGrupo = new TextArea("Descripcion", "descripcion");
        tex_descripcionGrupo.setWidth(200);




        formPanel.add(tex_nombreGrupo);
        formPanel.add(tex_descripcionGrupo);

        decPanel.add(formPanel);

        add(decPanel);
        initValues();
    }

    


    private void initValues() {
        tex_nombreGrupo.setValue(descripcionGrupo);
        tex_descripcionGrupo.setValue(nombreGrupo);



    }

    public Button getBut_aceptar() {
        return but_aceptarGrupo;
    }

    public Button getBut_cancelar() {
        return but_cancelarGrupo;
    }

    public void GuardarNuevoGrupo() {
        String cadena = "php/Grupo.php?funcion=GuardarNuevoGrupo&idgrupo=" + idgrupo;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando...", "guardar");
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
                            close();
                            padre.reload();
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

    public void GuardarEditarGrupo() {
        String cadena = "php/Grupo.php?funcion=GuardarEditarGrupo&idgrupo=" + idgrupo;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando....", "guardar");
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
                            close();
                            padre.reload();
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
