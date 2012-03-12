/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.egresos;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.FormLayout;
import java.util.Date;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author Uvillazon
 */
public class RealizarEgreso extends FormPanel {

    private ComboBox cuentas;
    private ComboBox encargados;
    private DateField fecha;
    private TextField total;
    private TextArea detalle;
    private TextField orden;
    private Button guardar;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("100%");
    private Object[][] encargadosM;
    private Object[][] cuentasM;
    private String cuenta;
    private String encargado;
    private String fechas;
    private String totales;
    private String detalles;
    private String idcuenta;
    private String idempleado;

    public RealizarEgreso(Object[][]cuentaM,Object[][] encargadoM){
//        this.idcuenta = idcuenta;
//        this.idempleado = idempleado;
//        fechas = fecha;
//        totales = total;
//        detalles = detalle;
        encargadosM = encargadoM;
        cuentasM = cuentaM;
        setClosable(true);
        setId("TPfun3002");
        setIconCls("tab-icon");
        setPaddings(30, 0, 0, 0);
        setAutoScroll(false);
        setTitle("Realizar Egreso");
        inicializar(false);
    }
    public RealizarEgreso(Object[][] cuentaM,String idcuenta, Object[][] encargadoM,String idempleado ,String fecha,String total,String detalle,String idegreso) {

        this.idcuenta = idcuenta;
        this.idempleado = idempleado;
        fechas = fecha;
        totales = total;
        detalles = detalle;
        encargadosM = encargadoM;
        cuentasM = cuentaM;
        setClosable(true);
        setId("TPfun3002");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Realizar Egreso");
        inicializar(true);
    }

    public void inicializar(boolean b) {

        orden = new TextField("Orden Produccion", "ordenproduccion");
        fecha = new DateField("Fecha", "fecha");
        fecha.setFormat("Y-m-d");
        
        fecha.setId("fecha");
        fecha.setValue(new Date());
        total = new TextField("Total", "total");
        detalle = new TextArea("Detalle", "detalle");

        guardar = new Button("Guardar",new ButtonListenerAdapter() {
            @Override
             public void onClick(Button button, EventObject e) {
                    // com.google.gwt.user.client.Window.alert("Cuantas veces");
                    guardar();

            }
        });
        initCombos();

        //anadirListener();
        Panel panel1 = new Panel();
        panel1.setLayout(new ColumnLayout());
        panel1.setBaseCls("x-plain");

        Panel columnOnePanel = new Panel();
        columnOnePanel.setLayout(new FormLayout());
        columnOnePanel.setBaseCls("x-plain");
        //columnOnePanel.add(com_tipoTransaccion1109, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(orden,ANCHO_LAYOUT_DATA);
        columnOnePanel.add(encargados, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(cuentas, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(fecha, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(total, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(detalle, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(guardar);

        panel1.add(columnOnePanel);

        add(panel1);
        if(b)
        {
            initValues();
        }
        
    }
    public void initValues(){
        encargados.setValue(encargado);
    }
    public void initCombos() {

        encargados = new ComboBox("Encargados", "encargado");
        SimpleStore encargadoStore = new SimpleStore(new String[]{"idempleado", "nombre"}, encargadosM);
        encargadoStore.load();


        encargados.setMinChars(1);
        encargados.setFieldLabel("Encargado");
        encargados.setStore(encargadoStore);
        encargados.setValueField("idempleado");
        encargados.setDisplayField("nombre");
        encargados.setForceSelection(true);
        encargados.setMode(ComboBox.LOCAL);
        encargados.setEmptyText("Buscar Encargado");
        encargados.setLoadingText("buscando...");
        encargados.setTypeAhead(true);
        encargados.setSelectOnFocus(true);
        encargados.setWidth(200);

        encargados.setHideTrigger(true);

        cuentas = new ComboBox("Ceuntas", "cuenta");
        SimpleStore cuentaStore = new SimpleStore(new String[]{"idcuenta", "nombre"}, cuentasM);
        cuentaStore.load();


        cuentas.setMinChars(1);
        cuentas.setFieldLabel("cuenta");
        cuentas.setStore(cuentaStore);
        cuentas.setValueField("idcuenta");
        cuentas.setDisplayField("nombre");
        cuentas.setForceSelection(true);
        cuentas.setMode(ComboBox.LOCAL);
        cuentas.setEmptyText("Buscar Cuenta");
        cuentas.setLoadingText("buscando...");
        cuentas.setTypeAhead(true);
        cuentas.setSelectOnFocus(true);
        cuentas.setWidth(200);

        cuentas.setHideTrigger(true);

    }

    public void guardar() {
        String cadena = "php/Egreso.php?funcion=GuardarNuevoEgreso";
        cadena =
                cadena + "&" + getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando el nuevo Egreso", "guardar");
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
//                            close();

                            limpiar();
                        } else {
                            Utils.setErrorPrincipal(mensajeR, "error");
                        }

                    }

                }

                public void onError(Request request, Throwable exception) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });

        } catch (RequestException ex) {
            ex.getMessage();
            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
        }
    }
public void limpiar(){
    cuentas.setValue("");
    encargados.setValue("");
    fecha.setValue(new Date());
    total.setValue("");
    detalle.setValue("");
    orden.setValue("");
}
//    private void addListeners() {
//         guardar.addListener(new ButtonListenerAdapter() {
//
//            @Override
//            public void onClick(Button button, com.gwtext.client.core.EventObject e) {
//               com.google.gwt.user.client.Window.alert("Cuantas veces");
//            }
//
//
//
//        });
//    }
}
