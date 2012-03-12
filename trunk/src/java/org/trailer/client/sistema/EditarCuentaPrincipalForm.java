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
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
//import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
//import com.gwtext.client.widgets.Panel;
//import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
//import com.gwtext.client.widgets.form.Field;
//import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;

import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author buggy
 */
public class EditarCuentaPrincipalForm extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoC;
    private TextField tex_nombreC;
    private TextArea tex_observacionC;
    private ComboBox com_empleadoC;
    private ComboBox com_estadoE;
    private ComboBox com_tipo;
    private ComboBox com_item;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idcuentaprincipal;
    private String nombreC;
    private String responsableC;
    private String idresponsableC;
    private String codigoC;
    private String tipoC;
    private String estadoC;
    private String item;
    private String observacionC;
//    private String estadoC;
    private Object[][] empleadoM;
    private String[] tipoempleadoM;
    private String[] estadoM;
    private String[] tipoM;
    private boolean nuevo;
    private CuentaPrincipal padre;

    public EditarCuentaPrincipalForm(String idcuentaprincipal, String codigo, String nombre, String tipo, CuentaPrincipal padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idcuentaprincipal = idcuentaprincipal;
        this.codigoC = codigo;
        this.nombreC = nombre;
        
        this.tipoC = tipo;
        if (tipoC == null) {
            tipoC = "ingresos";
        }

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nueva Cuenta Principal";

        if (idcuentaprincipal != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Cuenta Principal";
            nuevo = false;
        } else {
            this.idcuentaprincipal = "nuevo";
            nuevo = true;

        }

        setId("win-Cuenta-Principal");
        but_aceptarP = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarEmpresa();
                } else {
                    GuardarNuevaEmpresa();
                }
            }
        });
        but_cancelarP = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarCuentaPrincipalForm.this.close();
                EditarCuentaPrincipalForm.this.setModal(false);
            //formulario = null;
            }
        });

        setTitle(tituloTabla);
        setAutoWidth(true);
        setAutoHeight(true);
        setLayout(new FitLayout());
        setPaddings(WINDOW_PADDING);
        setButtonAlign(Position.CENTER);
        addButton(but_aceptarP);
        addButton(but_cancelarP);

        setCloseAction(Window.CLOSE);
        setPlain(true);
        formPanel = new FormPanel();
        formPanel.setBaseCls("x-plain");


        tex_codigoC = new TextField("Abreviacion", "codigo", 100);
        tex_codigoC.setMaxLength(6);
        tex_nombreC = new TextField("Nombre", "nombre", 200);

     
        com_tipo = new ComboBox("Tipo de Cuenta", "tipo");
       
   formPanel.add(tex_codigoC);
        formPanel.add(tex_nombreC);
  
        formPanel.add(com_tipo);
        




//        formPanel.add(com_estadoC);

        add(formPanel);
        initCombos();
        initValues();
     

    }

    private void initCombos() {

        

      
        tipoM = new String[]{"ingresos", "Egresos","Impuestos"};

        SimpleStore tiposStore3 = new SimpleStore("tipo", tipoM);
        tiposStore3.load();
        com_tipo.setDisplayField("tipo");
        com_tipo.setStore(tiposStore3);

    }



    private void initValues() {
        tex_codigoC.setValue(codigoC);
        tex_nombreC.setValue(nombreC);

     
        com_tipo.setValue(tipoC);
    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevaEmpresa() {
        String cadena = "php/CuentaPrincipal.php?funcion=GuardarNuevaCuentaPrincipal";
        cadena =
                cadena + "&" + formPanel.getForm().getValues();
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

    public void GuardarEditarEmpresa() {
        String cadena = "php/CuentaPrincipal.php?funcion=GuardarEditarCuentaPrincipal&idcuenta=" + idcuentaprincipal;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando los cambios....", "guardar");
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