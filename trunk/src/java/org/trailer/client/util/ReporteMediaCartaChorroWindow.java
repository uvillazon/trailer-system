/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.util;

/**
 *
 * @author Edwin
 */
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Frame;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import org.trailer.client.util.Utils;

public class ReporteMediaCartaChorroWindow extends Window {

    private final int ANCHOClienteWin = 820;
    private final int ALTOClienteWin = 600;
    private final int WINDOW_PADDING = 5;
    private ToolbarButton imprimirR;
    private ToolbarButton guardarXLS;
    private ToolbarButton guardarPDF;
    private Panel panel;
    private String mostar;
    private String enlaceReporte;
    private String enlaceReporteXLS;
    private String enlaceReportePDF;
    private Button but_cancelarP;

    public ReporteMediaCartaChorroWindow(String enlace) {
        setTitle("Reporte");
        this.enlaceReporte = "php/ReporteHTML.php?" + enlace;
        this.enlaceReporteXLS = "php/ReporteXLS.php?" + enlace;
        this.enlaceReportePDF = "php/ReportePDF.php?" + enlace;
        setWidth(ANCHOClienteWin);
        setMinWidth(ANCHOClienteWin);
        setHeight(ALTOClienteWin);
        setAutoScroll(true);
        setLayout(new FitLayout());
        setPaddings(5);
        setButtonAlign(Position.CENTER);
        setCloseAction(Window.CLOSE);
        setPlain(true);
        onModuleLoad();
    }

    public ReporteMediaCartaChorroWindow(String enlace, String reporte) {
        setTitle("Reporte");

        this.enlaceReporte = "http://200.58.77.254:8080/report/" + enlace;
//        this.enlaceReporteXLS = "php/ReporteXLS.php?" + enlace;
//        this.enlaceReportePDF = "php/ReportePDF.php?" + enlace;
        setWidth(1000);
        setMinWidth(100);
        setHeight(ALTOClienteWin);
        setAutoScroll(true);
        setLayout(new FitLayout());
        setPaddings(5);
        setButtonAlign(Position.CENTER);
        setCloseAction(Window.CLOSE);
        setPlain(true);
        onModuleLoad1();
    }

    public ReporteMediaCartaChorroWindow(String enlace, boolean vistaPrevia) {
        setTitle("Reporte");
        this.enlaceReporte = enlace;
        this.enlaceReporteXLS = null;
        this.enlaceReportePDF = null;
        setWidth(ANCHOClienteWin);
        setMinWidth(ANCHOClienteWin);
        setHeight(ALTOClienteWin);
        setAutoScroll(true);
        setLayout(new FitLayout());
        setPaddings(5);
        setButtonAlign(Position.CENTER);
        setCloseAction(Window.CLOSE);
        setPlain(true);
        onModuleLoad();
        imprimirR.setDisabled(true);
        guardarXLS.setDisabled(true);
        guardarPDF.setDisabled(true);
    }

    public void onModuleLoad() {

//        panel = new Panel();
//        panel.setId("MediaCartaChorroWindow");
//        panel.setBorder(false);
//        panel.setPaddings(15);
//        panel.setLayout(new FitLayout());
//        panel.setAutoHeight(false);
//        panel.setAutoScroll(true);


        Utils.setErrorPrincipal("Cargando el reporte .... ", "cargar");
        final Conector conec = new Conector(enlaceReporte, false);

        try {
            conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                public void onResponseReceived(Request request, Response response) {
                    Utils.setErrorPrincipal("El reporte fue cargado correctamente", "mensaje");
                    String data = response.getText();
                    setHtml(data);
                    mostar = data;
                }

                public void onError(Request request, Throwable exception) {
                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                }
            });

        } catch (RequestException ex) {
            ex.getMessage();
            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
        }

        imprimirR = new ToolbarButton("Imprimir");
        guardarXLS = new ToolbarButton("Guardar XLS");
        guardarPDF = new ToolbarButton("Guardar PDF");

        imprimirR.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
//                String asdf = ReporteMediaCartaChorroWindow.this.getHtml().toString();
//                com.google.gwt.user.client.Window.alert(asdf);
//                com.google.gwt.user.client.Window.alert(mostar);
                Utils.imprimirDivSinCabecera(mostar);
            }
        });
        guardarXLS.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                com.google.gwt.user.client.Window.Location.assign(enlaceReporteXLS);
            }
        });
        guardarPDF.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                com.google.gwt.user.client.Window.Location.assign(enlaceReportePDF);
            }
        });
//        add(panel);

        Toolbar botonesPiePagina = new Toolbar();
        botonesPiePagina.addButton(imprimirR);
        botonesPiePagina.addButton(guardarXLS);
        botonesPiePagina.addButton(guardarPDF);

        setBottomToolbar(botonesPiePagina);
    }

    private void onModuleLoad1() {
        String nombreBoton2 = "Cerrar";
        but_cancelarP = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {

                ReporteMediaCartaChorroWindow.this.destroy();
                ReporteMediaCartaChorroWindow.this.close();
                ReporteMediaCartaChorroWindow.this.clear();
//                ReporteMediaCartaChorroWindow.this.setModal(false);

            }
        });

//        setPaddings(WINDOW_PADDING);
        setButtonAlign(Position.CENTER);
        //addButton(but_aceptarP);
        addButton(but_cancelarP);
        Frame pdfFrame = new Frame();
        pdfFrame.setSize("100%", "100%");

        String parametro1 = "ROJO";

        //sin parametros
        pdfFrame.setUrl(enlaceReporte);
//        pdfFrame.setUrl("http://192.168.1.4:8080/report/index.jsp?dato=" + parametro1);
//        pdfFrame.setUrl("http://google.com");
//        pdfFrame.setUrl("http://192.168.1.4:8080/");


        //pdfFrame.setUrl("http://localhost:8080/reportes/index.jsp?dato=col-1");

        add(pdfFrame);
        setCloseAction(Window.CLOSE);
    }
}
