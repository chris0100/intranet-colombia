/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import com.eurekaintranet.entities.T001Usuario;
import com.eurekaintranet.entities.T002Rol;
import com.eurekaintranet.entities.T020ListaEmpaque;
import com.eurekaintranet.entities.T021Caja;
import com.eurekaintranet.entities.T022ListaEmpaqueHasCaja;
import com.eurekaintranet.entities.T0231ConfigImpresoraLabel;
import com.eurekaintranet.sessions.T020ListaEmpaqueFacade;
import com.eurekaintranet.sessions.T021CajaFacade;
import com.eurekaintranet.sessions.T022ListaEmpaqueHasCajaFacade;
import com.eurekaintranet.sessions.T0231ConfigImpresoraLabelFacade;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.holders.ShortHolder;

import org.apache.axis.message.MessageElement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.tempuri.ImportarXMLResponseImportarXMLResult;
import org.tempuri.WSUNOEESoapProxy;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * @author Lccabalr
 */
@Named(value = "listaEmpaqueController")
@SessionScoped
public class ListaEmpaqueController implements Serializable {

    @EJB
    private T020ListaEmpaqueFacade listaEmpaqueFacade;
    private T020ListaEmpaque listaEmpaqueActual;
    private List<T020ListaEmpaque> listaEmpaque;

    @EJB
    private T021CajaFacade cajaFacade;
    private T021Caja cajaActual;
    private List<T021Caja> listaCaja;

    @EJB
    private T022ListaEmpaqueHasCajaFacade listaEmpaqueHasCajaFacade;
    private T022ListaEmpaqueHasCaja listaEmpaqueHasCajaActual;
    private List<T022ListaEmpaqueHasCaja> listaEmpaqueHasCaja;

    private T001Usuario usuarioActual;

    @EJB
    private T0231ConfigImpresoraLabelFacade configImpresoraFacade;
    private T0231ConfigImpresoraLabel configImpresoraActual;

    private List<String> listaNitCliente;
    private String nitRazonSocial;
    private int caja = 1;

    private ItemPedidoUnoeeController itemPedidoUnoeeActual;
    private List<ItemPedidoUnoeeController> listaItemPedidoUnoee;
    private List<String> listaPedido;
    private String pedido;

    private String razonSocialCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private String paisCliente;
    private String ciudadCliente;
    private String nombreResponsable;
    private String correoResponsable;

    private String codigoBarraItem;
    private String idBarraPrincipal;
    private int cantCodBarra;
    private int contarPartidasArancelarias = 0;
    private String codPartidaArancelaria = "";
    private String descPartidaArancelaria = "";
    private int primeraPartidaArancelaria = 0;
    private boolean estadoInputCodBarra;
    private int contarItemsAgregados;
    private String pedidosCaja = "";
    private String ocCaja = "";
    private boolean estadoCaja = false;
    private boolean estadoListaEmpaque = false;
    private boolean estadoListaEmpaquePDF = false;
    private boolean estadoDimensionCaja = false;
    private String nombreBotonGenerarPDF;
    private String consultaFacturaIdMovimientos;
    private BigDecimal pesoNetoSuma = new BigDecimal(0);
    private boolean cajaNueva = false;

    private BigDecimal sumaTotalPesoBruto = new BigDecimal(0);
    private BigDecimal sumaTotalPesoNeto = new BigDecimal(0);
    private BigDecimal sumaPrecioItem = new BigDecimal(0);
    private int sumaCantCaja = 0;
    private int sumaTotalCantCaja = 0;
    private int sumaTotalItem = 0;

    private String mensajeConector = "";
    private int valorConector = 0;

    private String ip = "";

    public ListaEmpaqueController() {
    }
    //GETTER AND SETTER

    public T020ListaEmpaqueFacade getListaEmpaqueFacade() {
        return listaEmpaqueFacade;
    }

    public void setListaEmpaqueFacade(T020ListaEmpaqueFacade listaEmpaqueFacade) {
        this.listaEmpaqueFacade = listaEmpaqueFacade;
    }

    public T020ListaEmpaque getListaEmpaqueActual() {
        return listaEmpaqueActual;
    }

    public void setListaEmpaqueActual(T020ListaEmpaque listaEmpaqueActual) {
        this.listaEmpaqueActual = listaEmpaqueActual;
    }

    public List<T020ListaEmpaque> getListaEmpaque() {
        return listaEmpaque = getListaEmpaqueFacade().consultarListaEmpaque();
    }

    public List<T020ListaEmpaque> getListaEmpaqueUsuario() {
        return listaEmpaque;
    }

    public void setListaEmpaque(List<T020ListaEmpaque> listaEmpaque) {
        this.listaEmpaque = listaEmpaque;
    }

    public T021CajaFacade getCajaFacade() {
        return cajaFacade;
    }

    public void setCajaFacade(T021CajaFacade cajaFacade) {
        this.cajaFacade = cajaFacade;
    }

    public T021Caja getCajaActual() {
        return cajaActual;
    }

    public void setCajaActual(T021Caja cajaActual) {
        this.cajaActual = cajaActual;
    }

    public List<T021Caja> getListaCaja() {
        return listaCaja = getCajaFacade().cajaListaEmpaque(listaEmpaqueActual);
    }

    public void setListaCaja(List<T021Caja> listaCaja) {
        this.listaCaja = listaCaja;
    }

    public T022ListaEmpaqueHasCajaFacade getListaEmpaqueHasCajaFacade() {
        return listaEmpaqueHasCajaFacade;
    }

    public void setListaEmpaqueHasCajaFacade(T022ListaEmpaqueHasCajaFacade listaEmpaqueHasCajaFacade) {
        this.listaEmpaqueHasCajaFacade = listaEmpaqueHasCajaFacade;
    }

    public T022ListaEmpaqueHasCaja getListaEmpaqueHasCajaActual() {
        return listaEmpaqueHasCajaActual;
    }

    public void setListaEmpaqueHasCajaActual(T022ListaEmpaqueHasCaja listaEmpaqueHasCajaActual) {
        this.listaEmpaqueHasCajaActual = listaEmpaqueHasCajaActual;
    }

    public List<T022ListaEmpaqueHasCaja> getListaEmpaqueHasCaja() {
        return listaEmpaqueHasCaja;
    }

    public void setListaEmpaqueHasCaja(List<T022ListaEmpaqueHasCaja> listaEmpaqueHasCaja) {
        this.listaEmpaqueHasCaja = listaEmpaqueHasCaja;
    }

    public List<String> getListaNitCliente() {
        return listaNitCliente;
    }

    public void setListaNitCliente(List<String> listaNitCliente) {
        this.listaNitCliente = listaNitCliente;
    }

    public String getNitRazonSocial() {
        return nitRazonSocial;
    }

    public void setNitRazonSocial(String nitRazonSocial) {
        this.nitRazonSocial = nitRazonSocial;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getPaisCliente() {
        return paisCliente;
    }

    public void setPaisCliente(String paisCliente) {
        this.paisCliente = paisCliente;
    }

    public String getCiudadCliente() {
        return ciudadCliente;
    }

    public void setCiudadCliente(String ciudadCliente) {
        this.ciudadCliente = ciudadCliente;
    }

    public String getRazonSocialCliente() {
        return razonSocialCliente;
    }

    public void setRazonSocialCliente(String razonSocialCliente) {
        this.razonSocialCliente = razonSocialCliente;
    }

    public String getCodigoBarraItem() {
        return codigoBarraItem;
    }

    public void setCodigoBarraItem(String codigoBarraItem) {
        this.codigoBarraItem = codigoBarraItem;
    }

    public String getCodPartidaArancelaria() {
        return codPartidaArancelaria;
    }

    public void setCodPartidaArancelaria(String codPartidaArancelaria) {
        this.codPartidaArancelaria = codPartidaArancelaria;
    }

    public String getDescPartidaArancelaria() {
        return descPartidaArancelaria;
    }

    public void setDescPartidaArancelaria(String descPartidaArancelaria) {
        this.descPartidaArancelaria = descPartidaArancelaria;
    }


    public List<String> getListaPedido() {
        return listaPedido;
    }

    public void setListaPedido(List<String> listaPedido) {
        this.listaPedido = listaPedido;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public boolean isEstadoInputCodBarra() {
        return estadoInputCodBarra;
    }

    public void setEstadoInputCodBarra(boolean estadoInputCodBarra) {
        this.estadoInputCodBarra = estadoInputCodBarra;
    }

    public ItemPedidoUnoeeController getItemPedidoUnoeeActual() {
        return itemPedidoUnoeeActual;
    }

    public void setItemPedidoUnoeeActual(ItemPedidoUnoeeController itemPedidoUnoeeActual) {
        this.itemPedidoUnoeeActual = itemPedidoUnoeeActual;
    }

    public List<ItemPedidoUnoeeController> getListaItemPedidoUnoee() {
        return listaItemPedidoUnoee;
    }

    public void setListaItemPedidoUnoee(List<ItemPedidoUnoeeController> listaItemPedidoUnoee) {
        this.listaItemPedidoUnoee = listaItemPedidoUnoee;
    }

    public T001Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(T001Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getPedidosCaja() {
        return pedidosCaja;
    }

    public void setPedidosCaja(String pedidosCaja) {
        this.pedidosCaja = pedidosCaja;
    }

    public boolean isEstadoCaja() {
        return estadoCaja;
    }

    public void setEstadoCaja(boolean estadoCaja) {
        this.estadoCaja = estadoCaja;
    }

    public boolean isEstadoListaEmpaque() {
        return estadoListaEmpaque;
    }

    public void setEstadoListaEmpaque(boolean estadoListaEmpaque) {
        this.estadoListaEmpaque = estadoListaEmpaque;
    }

    public boolean isEstadoListaEmpaquePDF() {
        return estadoListaEmpaquePDF;
    }

    public void setEstadoListaEmpaquePDF(boolean estadoListaEmpaquePDF) {
        this.estadoListaEmpaquePDF = estadoListaEmpaquePDF;
    }

    public String getNombreBotonGenerarPDF() {
        return nombreBotonGenerarPDF;
    }

    public void setNombreBotonGenerarPDF(String nombreBotonGenerarPDF) {
        this.nombreBotonGenerarPDF = nombreBotonGenerarPDF;
    }

    public boolean isEstadoDimensionCaja() {
        return estadoDimensionCaja;
    }

    public void setEstadoDimensionCaja(boolean estadoDimensionCaja) {
        this.estadoDimensionCaja = estadoDimensionCaja;
    }

    public BigDecimal getPesoNetoSuma() {
        return pesoNetoSuma;
    }

    public void setPesoNetoSuma(BigDecimal pesoNetoSuma) {
        this.pesoNetoSuma = pesoNetoSuma;
    }

    public BigDecimal getSumaTotalPesoBruto() {
        return sumaTotalPesoBruto;
    }

    public void setSumaTotalPesoBruto(BigDecimal sumaTotalPesoBruto) {
        this.sumaTotalPesoBruto = sumaTotalPesoBruto;
    }

    public BigDecimal getSumaTotalPesoNeto() {
        return sumaTotalPesoNeto;
    }

    public void setSumaTotalPesoNeto(BigDecimal sumaTotalPesoNeto) {
        this.sumaTotalPesoNeto = sumaTotalPesoNeto;
    }

    public int getSumaTotalCantCaja() {
        return sumaTotalCantCaja;
    }

    public void setSumaTotalCantCaja(int sumaTotalCantCaja) {
        this.sumaTotalCantCaja = sumaTotalCantCaja;
    }

    public int getSumaCantCaja() {
        return sumaCantCaja;
    }

    public void setSumaCantCaja(int sumaCantCaja) {
        this.sumaCantCaja = sumaCantCaja;
    }

    public int getSumaTotalItem() {
        return sumaTotalItem;
    }

    public void setSumaTotalItem(int sumaTotalItem) {
        this.sumaTotalItem = sumaTotalItem;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getCorreoResponsable() {
        return correoResponsable;
    }

    public void setCorreoResponsable(String correoResponsable) {
        this.correoResponsable = correoResponsable;
    }

    public String getOcCaja() {
        return ocCaja;
    }

    public void setOcCaja(String ocCaja) {
        this.ocCaja = ocCaja;
    }

    public BigDecimal getSumaPrecioItem() {
        return sumaPrecioItem;
    }

    public void setSumaPrecioItem(BigDecimal sumaPrecioItem) {
        this.sumaPrecioItem = sumaPrecioItem;
    }

    public T0231ConfigImpresoraLabelFacade getConfigImpresoraFacade() {
        return configImpresoraFacade;
    }

    public void setConfigImpresoraFacade(T0231ConfigImpresoraLabelFacade configImpresoraFacade) {
        this.configImpresoraFacade = configImpresoraFacade;
    }

    public T0231ConfigImpresoraLabel getConfigImpresoraActual() {
        return configImpresoraActual;
    }

    public void setConfigImpresoraActual(T0231ConfigImpresoraLabel configImpresoraActual) {
        this.configImpresoraActual = configImpresoraActual;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isTipoListaEmpaqueNacional() {
        try {
            if (listaEmpaqueActual.getTipoListaEmpaque().equals("NACIONAL")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            return true;
        }
    }

    //Navigation
    public String prepareReporte() {
        listaEmpaqueHasCaja = getListaEmpaqueHasCajaFacade().consultarItemListaEmpaque(listaEmpaqueActual);
        return "/administrador/modListaEmpaque/listaEmpaque/reporteItems";
    }

    public void recargarLista() {
        razonSocialCliente = "";
        telefonoCliente = "";
        direccionCliente = "";
        ciudadCliente = "";
        paisCliente = "";
        nitRazonSocial = "";
        contarPartidasArancelarias = 0;
        codPartidaArancelaria = "";
        descPartidaArancelaria = "";
        listaNitCliente = getListaEmpaqueFacade().consultarNit();
    }

    public void cambiarEstadoInputCodBarra(SelectEvent event) throws SQLException {
        String selectedItem = (String) event.getObject().toString();
        contarItemsAgregados = 0;
        List<T022ListaEmpaqueHasCaja> listaQuitar = new ArrayList<>();
        if (selectedItem.equals("seleccionar")) {
            estadoInputCodBarra = true;
        } else {
            for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                if (item.getEstado() == 1) {
                    listaQuitar.add(item);
                }
            }
            listaEmpaqueHasCaja = listaQuitar;
            listaItemPedidoUnoee = new ArrayList<>();
            consultarItemPedido();
            estadoInputCodBarra = false;
        }
        if (contarItemsAgregados > 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ítems repetidos", contarItemsAgregados + " ítems ya se habían agregado anteriormente");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void obtenerCodListaEmpaque() {
        int totalListaEmpaque = 0;
        for (T020ListaEmpaque item : getListaEmpaqueFacade().findAll()) {
            totalListaEmpaque++;
        }
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        listaEmpaqueActual.setCodigoLista(String.valueOf(year) + String.format("%05d", totalListaEmpaque + 1));
    }


    //Prepara la lista de empaque
    public String prepareListaEmpaque() {
        listaEmpaque = getListaEmpaqueFacade().consultarListaEmpaqueUsuario(usuarioActual);

        for (T002Rol item : usuarioActual.getT002RolList()) {
            if (item.getIdRol().equals("lpventa")) {
                listaEmpaque = getListaEmpaqueFacade().consultarListaEmpaqueTipo("INTERNACIONAL");
                break;
            }
        }
        return "/administrador/modListaEmpaque/listaEmpaque/lista";
    }


    //Prepara la lista completa
    public String prepareListaEmpaqueCompleta() {
        return "/administrador/modListaEmpaque/listaEmpaque/listaCompleta";
    }


    //Prepara la lista de empaq
    public String prepareCreateListaEmpaqueNa() {
        caja = 1;
        recargarLista();
        listaEmpaqueActual = new T020ListaEmpaque();
        estadoListaEmpaque = false;
        obtenerCodListaEmpaque();
        listaEmpaqueActual.setFechaCreacion(new Date());
        listaEmpaqueActual.setTipoListaEmpaque("NACIONAL");
        return "crear";
    }


    public String prepareCreateListaEmpaqueIn() {
        caja = 1;
        recargarLista();
        listaEmpaqueActual = new T020ListaEmpaque();
        estadoListaEmpaque = false;
        obtenerCodListaEmpaque();
        listaEmpaqueActual.setFechaCreacion(new Date());
        listaEmpaqueActual.setTipoListaEmpaque("INTERNACIONAL");
        return "crear";
    }

    public String prepareViewListaEmpaque() throws SQLException {
        nitRazonSocial = listaEmpaqueActual.getNit();
        consultarDatosCliente();
        estadoListaEmpaque = true;
        consultaFacturaIdMovimientos = null;
        sumaTotalPesoBruto = new BigDecimal(0);
        sumaTotalPesoNeto = new BigDecimal(0);
        sumaTotalCantCaja = 0;
        sumaTotalItem = 0;
        sumaPrecioItem = new BigDecimal(0);
        if (listaEmpaqueActual.getEstado() == 1) {
            estadoListaEmpaquePDF = false;
            nombreBotonGenerarPDF = "Cerrar lista de empaque";
        } else if (listaEmpaqueActual.getEstado() > 1) {
            estadoListaEmpaquePDF = false;
            nombreBotonGenerarPDF = "Imprimir lista de empaque";
        } else {
            estadoListaEmpaquePDF = true;
            nombreBotonGenerarPDF = "Cerrar lista de empaque";
        }
        for (T021Caja item : getCajaFacade().cajaListaEmpaque(listaEmpaqueActual)) {
            sumaTotalPesoBruto = sumaTotalPesoBruto.add(item.getPesoBruto());
            sumaTotalPesoNeto = sumaTotalPesoNeto.add(item.getPesoNeto());
        }
        for (T022ListaEmpaqueHasCaja item : getListaEmpaqueHasCajaFacade().consultarItemListaEmpaque(listaEmpaqueActual)) {
            try {
                sumaTotalCantCaja = item.getCantPaquete() + sumaTotalCantCaja;
                sumaTotalItem = item.getCantCodBarraCaja() + sumaTotalItem;
            } catch (Exception e) {
                System.out.println("error" + e);
            }
        }
        sumaPrecioItem = consultarPrecioListaEmpaque(listaEmpaqueActual.getIdListaEmpaque());
        return "ver";
    }

    public String prepareEditListaEmpaque() throws SQLException {
        if (listaEmpaqueActual.getEstado() == 0) {
            caja = 1;
            for (T021Caja item : cajaFacade.cajaListaEmpaque(listaEmpaqueActual)) {
                caja++;
            }
            nitRazonSocial = listaEmpaqueActual.getNit();
            consultarDatosCliente();
            estadoListaEmpaque = true;
            return "crear";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La lista de empaque no se puede modificar", "La lista de empaque " + listaEmpaqueActual.getCodigoLista() + " no se puede modificar por que esta en proceso de remisión y facturación");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }

    }

    public String prepareCreateCaja() throws SQLException {
        caja = 1;
        for (T021Caja item : listaCaja) {
            caja++;
        }
        if (caja == 1) {
            listaEmpaqueActual.setNit(nitRazonSocial);
            listaEmpaqueActual.setRazonSocial(razonSocialCliente);
            listaEmpaqueActual.setEstado((short) 0);
            listaEmpaqueActual.setIdUsuarioCreacion(usuarioActual);
            listaEmpaqueActual.setFechaCreacion(new Date());
            listaEmpaqueActual.setEstadoWb(false);
            listaEmpaqueActual.setMensajeWb("");
            if (listaEmpaqueActual.getIdListaEmpaque() == null) {
                getListaEmpaqueFacade().create(listaEmpaqueActual);
            } else {
                getListaEmpaqueFacade().edit(listaEmpaqueActual);
            }
        }
        cajaActual = new T021Caja();
        cajaActual.setEstado((short) 1);
        cajaActual.setFechaCreacion(new Date());
        estadoCaja = false;
        estadoDimensionCaja = false;
        codigoBarraItem = "";
        pesoNetoSuma = new BigDecimal(0);
        idBarraPrincipal = "";
        listaEmpaqueHasCaja = new ArrayList<>();
        codPartidaArancelaria = "";
        descPartidaArancelaria = "";
        listaPedido = getListaEmpaqueFacade().consultarPedido(nitRazonSocial);
        estadoInputCodBarra = true;
        pedido = "";
        primeraPartidaArancelaria = 0;
        cajaNueva = true;
        sumaCantCaja = 0;
        //actualizarCantComprometidaBase();
        return "/administrador/modListaEmpaque/caja/crear";
    }


    public String prepareEditCaja() throws SQLException {
        codigoBarraItem = "";
        pedido = "";
        pesoNetoSuma = new BigDecimal(0);
        estadoDimensionCaja = true;
        listaPedido = getListaEmpaqueFacade().consultarPedido(nitRazonSocial);
        cajaNueva = false;
        sumaCantCaja = 0;
        llenarListaCajaItems(cajaActual);
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
            pesoNetoSuma = (item.getPeso().multiply(new BigDecimal(item.getCantCodBarraCaja()))).add(pesoNetoSuma);
        }
        for (T022ListaEmpaqueHasCaja item : getListaEmpaqueHasCajaFacade().consultarItemListaEmpaqueCaja(listaEmpaqueActual, cajaActual)) {
            sumaTotalCantCaja = item.getCantPaquete() + sumaTotalCantCaja;
        }
        if (cajaActual.getPartidaArancelaria() != null) {
            codPartidaArancelaria = cajaActual.getPartidaArancelaria().substring(0, 14);
            descPartidaArancelaria = cajaActual.getPartidaArancelaria().substring(16, cajaActual.getPartidaArancelaria().length());
        } else {
            codPartidaArancelaria = "";
            descPartidaArancelaria = "";
        }
        if (cajaActual.getEstado() == 1) {
            estadoCaja = true;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Esta caja no se puede modificar", "Esta caja se encuentra en estado cerrada, por lo tanto no es posible editarla");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {
            estadoCaja = false;
        }
        caja = cajaActual.getNumeroCaja();
        //actualizarCantComprometidaBase();
        return "/administrador/modListaEmpaque/caja/editar";
    }


    //Guardar codigo de barras
    public void addCodBarraCaja() {
        try {
            idBarraPrincipal = codigoBarraItem.substring(2, 12);
            cantCodBarra = Integer.parseInt(codigoBarraItem.substring(12, 18));
            //actualizarCantComprometidaBase();
            if (codigoBarraItem.length() <= 18) {
                if (cantCodBarra > 0) {
                    agregarCodBarrasItem();
                } else {
                    FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ítem no agregado", "La cantidad debe ser mayor a 0");
                    RequestContext.getCurrentInstance().showMessageInDialog(message2);
                }
            } else {
                FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ítem no agregado", "El código de barras no se ajusta al tamaño requerido de 18 caracteres");
                RequestContext.getCurrentInstance().showMessageInDialog(message2);
            }
            if (primeraPartidaArancelaria == 0 && codigoBarraItem.equals("")) {
                codPartidaArancelaria = listaEmpaqueHasCajaActual.getPartidaArancelaria();
                descPartidaArancelaria = listaEmpaqueHasCajaActual.getDescPartidaArancelaria();
                primeraPartidaArancelaria++;
                estadoDimensionCaja = true;
            }
        } catch (Exception e) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ítem no agregado", "El código de barras se encuentra errado");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
        }
    }


    public String addCaja() {
        try {
            return "crearListaEmpaque";
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> completeNit(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaNitCliente) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }

    public List<String> completePedido(String query) {
        List<String> results = new ArrayList<String>();
        for (String text : listaPedido) {
            if (text.contains(query.toUpperCase())) {
                results.add(text);
            }
        }
        return results;
    }

    private void addErrorMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    private void addSuccessMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    //Datos cliente
    public void consultarDatosCliente() throws SQLException {
        if (nitRazonSocial.length() > 15) {
            nitRazonSocial = nitRazonSocial.substring(0, 15).replaceFirst("^0*", "");
        }
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;

        String query = "select f200_razon_Social razonSocial, f013_descripcion ciudad, f015_direccion1 direccion, f011_descripcion pais, f015_telefono telefono, t015.f015_contacto nombre, f015_email email from [UnoEE].[dbo].t200_mm_terceros t200 inner join [UnoEE].[dbo].t015_mm_contactos t015 on t200.f200_rowid_contacto = t015.f015_rowid left outer join [UnoEE].[dbo].t011_mm_paises	on	f015_id_pais = f011_id left outer join [UnoEE].[dbo].t012_mm_deptos on f015_id_depto = f012_id and f015_id_pais = f012_id_pais left outer join [UnoEE].[dbo].t013_mm_ciudades on	f015_id_ciudad = f013_id and f015_id_pais = f013_id_pais and f015_id_depto = f013_id_depto where t200.f200_nit = '" + nitRazonSocial + "'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                setRazonSocialCliente(rs.getString("razonSocial"));
                setTelefonoCliente(rs.getString("telefono"));
                setPaisCliente(rs.getString("pais"));
                setDireccionCliente(rs.getString("direccion"));
                setCiudadCliente(rs.getString("ciudad"));
                setNombreResponsable(rs.getString("nombre"));
                setCorreoResponsable(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }



    //Guardar cod. barras item
    public void agregarCodBarrasItem() throws SQLException {
        int itemEncontrado = 0;
        BigDecimal topeCaja = cajaActual.getIdDimension().getPeso().multiply(new BigDecimal(0.10)).add(cajaActual.getIdDimension().getPeso()).setScale(1, BigDecimal.ROUND_HALF_EVEN);
        boolean itemDoble = false;
        int contItemDoble = 1;


        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
            if (idBarraPrincipal.equals(item.getCodBarras()) && item.getCantComprometida().compareTo(new BigDecimal(item.getCantCodBarra())) != 0) {
                itemEncontrado = 1;
                validarPosArancelaria(item.getCodigoItem());
                BigDecimal pesoNetoCaja = (item.getPeso().multiply(new BigDecimal(cantCodBarra))).add(pesoNetoSuma);
                itemDoble = false;
                if (listaEmpaqueActual.getTipoListaEmpaque().equals("NACIONAL")) {
                    contarPartidasArancelarias = 1;
                }


                if (pesoNetoCaja.compareTo(topeCaja) <= 0) {
                    switch (contarPartidasArancelarias) {
                        case 0:
                            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener partida arancelaria", "El ítem no tiene partida arancelaria asociada");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            break;

                        case 1:
                            BigDecimal convertDecimal;
                            convertDecimal = BigDecimal.valueOf(cantCodBarra + item.getCantCodBarra());
                            if (convertDecimal.compareTo(item.getCantComprometida()) <= 0) {
                                if (primeraPartidaArancelaria == 0) {
                                    item.setDescPais("");
                                    item.setDescPais(listaEmpaqueHasCajaFacade.obtenerDescItemPais(getPaisCliente(), item.getCodigoItem()));

                                    if (item.getDescPais().equals("")) {
                                        item.setDescPais(item.getDescripcion());
                                    }

                                    item.setCantCodBarra(item.getCantCodBarra() + cantCodBarra);
                                    item.setCantCodBarraCaja(item.getCantCodBarraCaja() + cantCodBarra);
                                    item.setEstado((short) 1);
                                    item.setCantPaquete(item.getCantPaquete() + 1);


                                    //BigDecimal valor = new BigDecimal(0);
                                    BigDecimal valor = consultarPrecioItem(item.getIdMovimiento()).multiply(new BigDecimal(item.getCantCodBarra()));
                                    item.setPrecioNeto(valor);
                                    item.setPrecioUnitario(consultarPrecioItem(item.getIdMovimiento()));
                                    listaEmpaqueHasCajaActual = item;
                                    addSuccessMessage("Ítem agregado correctamente", "El ítem fue agregado correctamente a la caja");
                                    contItemDoble = 0;
                                    pesoNetoSuma = pesoNetoCaja;
                                    sumaCantCaja++;
                                    codigoBarraItem = "";
                                    if (pesoNetoCaja.compareTo(cajaActual.getIdDimension().getPeso()) > 0) {
                                        FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya se supero el peso máximo de la caja", "El peso máximo de la caja es de " + cajaActual.getIdDimension().getPeso());
                                        RequestContext.getCurrentInstance().showMessageInDialog(message2);
                                    }
                                } else if (codPartidaArancelaria.equals(item.getPartidaArancelaria()) || listaEmpaqueActual.getTipoListaEmpaque().equals("NACIONAL")) {
                                    item.setDescPais("");
                                    item.setDescPais(listaEmpaqueHasCajaFacade.obtenerDescItemPais(getPaisCliente(), item.getCodigoItem()));
                                    if (item.getDescPais().equals("")) {
                                        item.setDescPais(item.getDescripcion());
                                    }
                                    item.setCantCodBarra(item.getCantCodBarra() + cantCodBarra);
                                    item.setCantCodBarraCaja(item.getCantCodBarraCaja() + cantCodBarra);
                                    item.setEstado((short) 1);
                                    //BigDecimal valor = new BigDecimal(0);
                                    BigDecimal valor = consultarPrecioItem(item.getIdMovimiento()).multiply(new BigDecimal(item.getCantCodBarra()));
                                    item.setPrecioNeto(valor);
                                    item.setPrecioUnitario(consultarPrecioItem(item.getIdMovimiento()));
                                    item.setCantPaquete(item.getCantPaquete() + 1);
                                    addSuccessMessage("Ítem agregado correctamente", "El ítem fue agregado correctamente a la caja");
                                    contItemDoble = 0;
                                    pesoNetoSuma = pesoNetoCaja;
                                    sumaCantCaja++;
                                    codigoBarraItem = "";

                                    if (pesoNetoCaja.compareTo(cajaActual.getIdDimension().getPeso()) > 0) {
                                        FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya se supero el peso máximo de la caja", "El peso máximo de la caja es de " + cajaActual.getIdDimension().getPeso() + " kg");
                                        RequestContext.getCurrentInstance().showMessageInDialog(message2);
                                    }
                                } else {
                                    FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener partida arancelaria", "La partida arancelaria del ítem no corresponde a " + codPartidaArancelaria + " - " + descPartidaArancelaria);
                                    RequestContext.getCurrentInstance().showMessageInDialog(message2);
                                }
                            } else {
                                contItemDoble++;
                                continue;
                            }
                            break;
                        default:
                            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener partida arancelaria", "Por favor revisar la partida arancelaria del item, ya que contiene múltiples partidas");
                            RequestContext.getCurrentInstance().showMessageInDialog(message2);
                            break;
                    }
                    break;
                } else {
                    FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el peso", "El peso neto no puede ser superior al peso tope de la caja, el peso tope de la caja es de " + topeCaja + " kg");
                    RequestContext.getCurrentInstance().showMessageInDialog(message2);
                    break;
                }
            }
        }
        if (itemEncontrado == 0) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Item no agregado", "El ítem no se encuentra disponible");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
        }
        if (contItemDoble > 1) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la cantidad", "La cantidad no puede superar a la cantidad comprometida");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
        }
    }

    //validar pos. arancelaria
    public void validarPosArancelaria(int codItem) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "select distinct f102_id id_pos_aranc, f102_descripcion descripcion from [UnoEE].dbo.t102_mc_pos_arancelarias inner join t133_mc_items_pos_arancelaria on f133_id_cia = 1 inner join v121 on v121.v121_rowid_item = f133_rowid_item and f133_id_pos_arancelaria = f102_id where f102_id_cia = 1 and v121_id_item = " + codItem + " order by f102_id";
        contarPartidasArancelarias = 0;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                contarPartidasArancelarias++;
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    //pos. arancelaria
    public void consultarPosArancelaria() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "select top 1 f102_id id_pos_aranc, f102_descripcion descripcion from [UnoEE].dbo.t102_mc_pos_arancelarias inner join t133_mc_items_pos_arancelaria on f133_id_cia = 1 inner join v121 on v121.v121_rowid_item = f133_rowid_item and f133_id_pos_arancelaria = f102_id where f102_id_cia = 1 and v121_id_item = " + listaEmpaqueHasCajaActual.getCodigoItem() + " order by f102_id";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaEmpaqueHasCajaActual.setPartidaArancelaria(rs.getString("id_pos_aranc"));
                listaEmpaqueHasCajaActual.setDescPartidaArancelaria(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    //items pedido
    public void consultarItemPedido() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "exec sp_pv_cons_pedido_item 1,'001','" + pedido.substring(0, 3).trim() + "','          ','   ','   ',0,0,'20190101','20190101',0,'20190101','20190101',0,1,10097,'EUREKA_LISTAEMPAQUE','          ',0,4300,0,0,1,NULL,NULL,1," + pedido.substring(4, pedido.length()) + "," + pedido.substring(4, pedido.length()) + "";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int prueba = 1;
            while (rs.next()) {
                rs.setFetchSize(1000);
                listaEmpaqueHasCajaActual = new T022ListaEmpaqueHasCaja();
                //BigDecimal cantidadComprometida = rs.getBigDecimal("f_cant_comp_parc_base");
                String estado = rs.getString("f_estado_movto");
                if (estado.contains("Comp")) {
                    prueba++;
                    listaEmpaqueHasCajaActual.setIdPedido(rs.getString("f_nrodocto"));
                    listaEmpaqueHasCajaActual.setIdMovimiento(rs.getInt("f_rowid_movto"));
                    listaEmpaqueHasCajaActual.setCodigoItem(rs.getInt("f_item"));
                    listaEmpaqueHasCajaActual.setDescripcion(rs.getString("f_desc_item"));
                    listaEmpaqueHasCajaActual.setIdExt1Detalle(rs.getString("f_ext_detalle_1"));
                    listaEmpaqueHasCajaActual.setDescripcionExt1(rs.getString("f_desc_ext_detalle_1_normal"));
                    listaEmpaqueHasCajaActual.setIdExt2Detalle(rs.getString("f_ext_detalle_2"));
                    listaEmpaqueHasCajaActual.setDescripcionExt2(rs.getString("f_desc_ext_detalle_2_normal"));
                    listaEmpaqueHasCajaActual.setPeso(new BigDecimal(0));
                    listaEmpaqueHasCajaActual.setUnidad(rs.getString("f_um_base"));
                    listaEmpaqueHasCajaActual.setCantPedida(rs.getBigDecimal("f_cant_pedida_base"));
                    listaEmpaqueHasCajaActual.setCantPendiente(rs.getBigDecimal("f_cant_pendiente_base"));
                    listaEmpaqueHasCajaActual.setCantComprometida(rs.getBigDecimal("f_cant_comprom_base"));
                    listaEmpaqueHasCajaActual.setCodBarras(rs.getString("f_codigo_barras_principal"));
                    consultarPosArancelaria();
                    listaEmpaqueHasCajaActual.setEstado((short) 0);
                    listaEmpaqueHasCajaActual.setFechaCreacion(new Date());
                    listaEmpaqueHasCajaActual.setIdBodega(rs.getString("f_bodega").trim());
                    listaEmpaqueHasCajaActual.setOc(rs.getString("f_orden_compra").trim());
                    listaEmpaqueHasCajaActual.setPrecioNeto(new BigDecimal(0));
                    listaEmpaqueHasCajaActual.setPrecioUnitario(new BigDecimal(0));
                    listaEmpaqueHasCajaActual.setNotasDocto(rs.getString("f_notas_docto"));
                    listaEmpaqueHasCajaActual.setNotasItem(rs.getString("f_notas_movto"));
                    boolean pedidoRepedido = false;
                    for (T022ListaEmpaqueHasCaja itemRepetido : listaEmpaqueHasCaja) {
                        if (listaEmpaqueHasCajaActual.getIdMovimiento() == itemRepetido.getIdMovimiento()) {
                            listaEmpaqueHasCajaActual = new T022ListaEmpaqueHasCaja();
                            pedidoRepedido = true;
                            break;
                        }
                    }
                    if (pedidoRepedido == false) {
                        listaEmpaqueHasCaja.add(listaEmpaqueHasCajaActual);
                    }

                }
            }
            llenarCantidadOtrasCajasCreando();
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void llenarCantidadOtrasCajas() {
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
            for (T022ListaEmpaqueHasCaja item2 : getListaEmpaqueHasCajaFacade().consultarItemListaEmpaque(listaEmpaqueActual)) {
                if (item2.getIdMovimiento() == item.getIdMovimiento()) {
                    item.setCantCodBarra(item2.getCantCodBarra());
                }
            }
        }
    }

    public void llenarCantidadOtrasCajasCreando() {
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
            for (T022ListaEmpaqueHasCaja item2 : getListaEmpaqueHasCajaFacade().consultarItemListaEmpaque(listaEmpaqueActual)) {
                if (item2.getIdMovimiento() == item.getIdMovimiento()) {
                    if (cajaActual.getEstado() > 0) {
                        item.setCantCodBarra(item2.getCantCodBarra() + item.getCantCodBarraCaja());
                    }
                }
            }
        }
    }

    //Cerrar caja
    public void cerrarCaja() {
        try {
            if (cajaActual.getPesoBruto().compareTo(cajaActual.getPesoNeto()) > 0) {
                if (contarItemsAgregados > 0) {
                    List<T022ListaEmpaqueHasCaja> listaEmpaqueHasCajaRepetido = getListaEmpaqueHasCajaFacade().consultarItemListaEmpaqueCaja(listaEmpaqueActual, cajaActual);
                    cajaActual.setEstado((short) 1);
                    cajaActual.setFechaCierre(new Date());
                    cajaActual.setResponsable(usuarioActual.toString());
                    cajaActual.setPartidaArancelaria(codPartidaArancelaria + " - " + descPartidaArancelaria);
                    cajaActual.setIdListaEmpaque(listaEmpaqueActual);
                    obtenerPedidosCaja();
                    obtenerOcCaja();
                    cajaActual.setPedidos(pedidosCaja);
                    cajaActual.setOc(ocCaja);
                    estadoCaja = true;
                    estadoListaEmpaque = true;
                    if (cajaNueva == true) {
                        cajaActual.setNumeroCaja(caja);
                        cajaFacade.create(cajaActual);
                        caja++;
                        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                            if (item.getEstado() == 1) {
                                item.setIdListaEmpaque(listaEmpaqueActual);
                                item.setIdCaja(cajaActual);
                                listaEmpaqueHasCajaFacade.create(item);
                            }
                        }
                    } else {
                        cajaFacade.edit(cajaActual);
                        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                            boolean itemRepetido = false;
                            if (item.getEstado() == 1) {
                                for (T022ListaEmpaqueHasCaja item2 : listaEmpaqueHasCajaRepetido) {
                                    if (item.getIdMovimiento() == item2.getIdMovimiento()) {
                                        itemRepetido = true;
                                        listaEmpaqueHasCajaRepetido.remove(item);
                                        break;
                                    } else {
                                        itemRepetido = false;
                                        break;
                                    }
                                }
                                if (itemRepetido == true) {
                                    item.setIdListaEmpaque(listaEmpaqueActual);
                                    item.setIdCaja(cajaActual);
                                    listaEmpaqueHasCajaFacade.edit(item);
                                } else {
                                    item.setIdListaEmpaque(listaEmpaqueActual);
                                    item.setIdCaja(cajaActual);
                                    listaEmpaqueHasCajaFacade.create(item);
                                }
                            }
                        }
                    }
                    cajaNueva = false;
                    ExecutorService executor = Executors.newFixedThreadPool(5);
                    executor.submit(() -> {
                        try {
                            actualizarCantComprometidaBase();
                            ejecutarWebService();
                            if (valorConector == 1) {
                                listaEmpaqueActual.setMensajeWb(mensajeConector);
                                listaEmpaqueActual.setEstadoWb(true);
                                getListaEmpaqueFacade().edit(listaEmpaqueActual);
                                estadoListaEmpaquePDF = true;
                            } else {
                                listaEmpaqueActual.setEstadoWb(false);
                                getListaEmpaqueFacade().edit(listaEmpaqueActual);

                            }
                        } catch (Exception e) {
                            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
                        }
                    });
                    addSuccessMessage("Datos comprometidos en el UnoEE", "Los datos fueron enviados al unoee");
                    addSuccessMessage("Caja cerrada correctamente", "La caja numero " + caja + " fue cerrada");
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la creación de la caja", "La caja no contiene ítems agregados");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la creación de la caja", "El peso bruto no puede ser menor que el peso neto");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        } catch (Exception e) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la creación de la caja", "La caja no fue creada por errores ");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
        }
    }

    public void calcularPesoNeto() {
        try {
            BigDecimal topeCaja = cajaActual.getIdDimension().getPeso().multiply(new BigDecimal(0.10)).add(cajaActual.getIdDimension().getPeso()).setScale(1, BigDecimal.ROUND_HALF_EVEN);
            if (cajaActual.getPesoBruto().compareTo(topeCaja) > 0) {
                FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el calculo del peso bruto", "El peso bruto no puede superar al peso tope de la caja, el peso tope de la caja es de " + topeCaja + "kg");
                RequestContext.getCurrentInstance().showMessageInDialog(message2);
                cajaActual.setPesoBruto(new BigDecimal(0));
                cajaActual.setPesoNeto(new BigDecimal(0));
            } else {
                cajaActual.setPesoNeto(cajaActual.getPesoBruto().subtract(cajaActual.getIdDimension().getPesoCorrugada()));
            }
        } catch (Exception e) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el calculo del peso neto", "El valor del peso neto no es posible");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
        }
    }

    //Borrar paquetes
    public void borrarPaqueteCaja() {
        try {
            int cont = 0;
            for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                if (item.getIdMovimiento() == listaEmpaqueHasCajaActual.getIdMovimiento()) {
                    listaEmpaqueHasCaja.remove(cont);
                    break;
                }
                cont++;
            }
            pedido = "seleccionar";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }

    //Caja en estado borrador
    public String guardarBorradorCaja() {
        try {
            cajaActual.setResponsable(usuarioActual.toString());
            cajaActual.setNumeroCaja(caja);
            cajaActual.setPartidaArancelaria(codPartidaArancelaria + " - " + descPartidaArancelaria);
            cajaActual.setIdListaEmpaque(listaEmpaqueActual);
            obtenerPedidosCaja();
            obtenerOcCaja();
            cajaActual.setPedidos(pedidosCaja);
            cajaActual.setOc(ocCaja);
            cajaActual.setPesoBruto(new BigDecimal(0));
            cajaActual.setPesoNeto(new BigDecimal(0));
            cajaActual.setEstado((short) 0);
            cajaFacade.create(cajaActual);
            addSuccessMessage("Caja guardada correctamente", "La caja numero " + caja + " fue guardada en borrador");
            caja++;
            for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                if (item.getEstado() == 1) {
                    item.setIdListaEmpaque(listaEmpaqueActual);
                    item.setIdCaja(cajaActual);
                    listaEmpaqueHasCajaFacade.create(item);
                }
            }
            ExecutorService executor = Executors.newFixedThreadPool(5);
            executor.submit(() -> {
                try {
                    actualizarCantComprometidaBase();
                    ejecutarWebService();
                    if (valorConector == 1) {
                        listaEmpaqueActual.setMensajeWb(mensajeConector);
                        listaEmpaqueActual.setEstadoWb(true);
                        getListaEmpaqueFacade().edit(listaEmpaqueActual);
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al comprometer en el UnoEE", "Los datos no fueron enviados al unoee");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        estadoListaEmpaquePDF = true;
                    } else {
                        listaEmpaqueActual.setEstadoWb(false);
                        getListaEmpaqueFacade().edit(listaEmpaqueActual);
                    }
                } catch (Exception e) {
                    addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
                }
            });
            return "/administrador/modListaEmpaque/listaEmpaque/crear";
        } catch (Exception e) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la creación de la caja", "La caja no fue creada por errores");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
            return null;
        }
    }

    public String editarBorradorCaja() {
        try {
            cajaActual.setResponsable(usuarioActual.toString());
            obtenerPedidosCaja();
            obtenerOcCaja();
            cajaActual.setPedidos(pedidosCaja);
            cajaActual.setOc(ocCaja);
            cajaFacade.edit(cajaActual);
            for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                if (item.getEstado() == 1) {
                    for (T022ListaEmpaqueHasCaja item2 : listaEmpaqueHasCajaFacade.consultarItemListaEmpaqueCaja(listaEmpaqueActual, cajaActual)) {
                        if (item.getIdListaEmpaque() == null && item.getIdCaja() == null) {
                            item.setIdListaEmpaque(listaEmpaqueActual);
                            item.setIdCaja(cajaActual);
                            listaEmpaqueHasCajaFacade.create(item);
                        } else {
                            listaEmpaqueHasCajaFacade.edit(item);
                        }
                    }

                }
            }
            addSuccessMessage("Caja guardada correctamente", "La caja numero " + caja + " fue guardada en borrador");
            ejecutarWebService();
            if (valorConector == 1) {
                listaEmpaqueActual.setMensajeWb(mensajeConector);
                listaEmpaqueActual.setEstadoWb(true);
                getListaEmpaqueFacade().edit(listaEmpaqueActual);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al comprometer en el UnoEE", "Los datos no fueron enviados al unoee");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                estadoListaEmpaquePDF = true;
            } else {
                listaEmpaqueActual.setEstadoWb(false);
                getListaEmpaqueFacade().edit(listaEmpaqueActual);
                addSuccessMessage("Datos comprometidos en el UnoEE", "Los datos fueron enviados al unoee");
            }
            return "/administrador/modListaEmpaque/listaEmpaque/crear";
        } catch (Exception e) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la creación de la caja", "La caja no fue creada por errores");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
            return null;
        }
    }

    public void prepareLabelCaja() {
        contarItemsAgregados = 0;
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
            if (item.getEstado() == 1) {
                contarItemsAgregados++;
            }
        }
        if (contarItemsAgregados == 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la creación de la caja", "La caja no contiene ítems agregados");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        cajaActual.setPesoNeto(pesoNetoSuma);
    }

    public void obtenerPedidosCaja() {
        try {
            List<String> listaPedido = new ArrayList<>();
            pedidosCaja = "";
            for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                if (item.getCantCodBarraCaja() > 0) {
                    listaPedido.add(item.getIdPedido());
                }
            }
            Set<String> hashSet = new HashSet<String>(listaPedido);
            listaPedido.clear();
            listaPedido.addAll(hashSet);
            for (String item2 : listaPedido) {
                if (pedidosCaja.equals("")) {
                    pedidosCaja = item2;
                } else {
                    pedidosCaja = pedidosCaja + ", " + item2;
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    public void obtenerOcCaja() {
        try {
            List<String> listaOc = new ArrayList<>();
            ocCaja = "";
            for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
                if (item.getCantCodBarraCaja() > 0) {
                    listaOc.add(item.getOc());
                }
            }
            Set<String> hashSet = new HashSet<String>(listaOc);
            listaOc.clear();
            listaOc.addAll(hashSet);
            for (String item2 : listaOc) {
                if (ocCaja.equals("")) {
                    ocCaja = item2;
                } else {
                    ocCaja = ocCaja + "," + item2;
                }
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

    }

    //CREAR LISTA DE EMPAQUE
    public String addListaEmpaque() {
        try {
            int contarBorrador = 0;
            if (listaCaja.isEmpty()) {
                addErrorMessage("Lista de empaque no creada", "La lista de empaque no tiene cajas creadas");
                return null;
            }
            for (T021Caja item : listaCaja) {
                if (item.getEstado() == 0) {
                    contarBorrador++;
                }
            }
            if (contarBorrador == 0) {
                listaEmpaqueActual.setFechaCierre(new Date());
                listaEmpaqueActual.setIdUsuarioCreacion(usuarioActual);
                listaEmpaqueActual.setRazonSocial(razonSocialCliente);
                listaEmpaqueActual.setNit(nitRazonSocial);
                mensajeConector = "";
                valorConector = 0;
                //ejecutarWebService();
                if (valorConector == 1) {
                    //listaEmpaqueActual.setMensajeWb(mensajeConector);
                    //listaEmpaqueActual.setEstado((short)0);
                    //listaEmpaqueActual.setEstadoWb(true);
                    //getListaEmpaqueFacade().edit(listaEmpaqueActual);
                    //estadoListaEmpaquePDF=true;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al compremeter en el UnoEE", "La lista de empaque no puede contener cajas en estado borrador");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    return "ver";
                } else {
                    listaEmpaqueActual.setEstado((short) 1);
                    listaEmpaqueActual.setEstadoWb(false);
                    getListaEmpaqueFacade().edit(listaEmpaqueActual);
                    addSuccessMessage("Lista de empaque creada", "Lista de empaque creada");
                    return "lista";
                }

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al compremeter en el UnoEE", "La lista de empaque no puede contener cajas en estado borrador");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                return null;
            }

        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }

    //REENVIAR CONECTOR
    public void reenviarConector() {
        try {
            if (listaEmpaqueActual.getEstado() == 1) {
                mensajeConector = "";
                valorConector = 0;
                ejecutarWebService();
                if (valorConector == 1) {
                    listaEmpaqueActual.setMensajeWb(mensajeConector);
                    listaEmpaqueActual.setEstado((short) 0);
                    listaEmpaqueActual.setEstadoWb(true);
                } else {
                    listaEmpaqueActual.setEstado((short) 1);
                    listaEmpaqueActual.setEstadoWb(false);
                }

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al compremeter en el UnoEE", "La lista de empaque no puede estar en estado cerrada o en borrador");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
    }

    //CONECTOR COMPROMISO
    public void ejecutarWebService() throws RemoteException, Exception {
        mensajeConector = "";
        valorConector = 0;
        String idListaEmpaque = String.valueOf(listaEmpaqueActual.getIdListaEmpaque());
        String pvstrDatos = getListaEmpaqueFacade().obtenerConectorListaEmpaque(idListaEmpaque);
        // TODO Auto-generated method stub
        WSUNOEESoapProxy proxy = new WSUNOEESoapProxy();
        ShortHolder holder = new ShortHolder((short) 0);
        ImportarXMLResponseImportarXMLResult result = proxy.importarXML(pvstrDatos, holder);
        MessageElement[] elements = result.get_any();
        for (MessageElement element : elements) {
            mensajeConector = mensajeConector + element.getAsString();
            valorConector = element.getLength();
        }
    }

    //CERRAR LISTA DE EMPAQUE
    public void prepareCreatePDF() throws SQLException {
        try {
            if (listaEmpaqueActual.getEstado() == 1) {
                organizarConsultaFactura();
                consultarFactura();
                if (!listaEmpaqueActual.getCodFactura().equals("")) {
                    listaEmpaqueActual.setEstado((short) 2);
                    listaEmpaqueFacade.edit(listaEmpaqueActual);
                    addSuccessMessage("Lista de empaque cerrada correctamente", "La lista de empaque fue cerrada con el código de factura " + listaEmpaqueActual.getCodFactura());
                    createPDF();
                } else {
                    FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el cierre de la lista de empaque", "La lista de empaque no fue cerrada debido a que no se encontro factura asociada");
                    RequestContext.getCurrentInstance().showMessageInDialog(message2);
                }
            } else {
                createPDF();
            }
        } catch (Exception e) {
            FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en el cierre de la lista de empaque", "La lista de empaque no fue cerrada debido a que no se encontro factura asociada");
            RequestContext.getCurrentInstance().showMessageInDialog(message2);
        }
    }

    //GENERAR LISTA DE EMPAQUE
    public void llenarListaCajaItems(T021Caja caja) {
        listaEmpaqueHasCaja = getListaEmpaqueHasCajaFacade().consultarItemListaEmpaqueCaja(listaEmpaqueActual, caja);
        llenarCantidadOtrasCajas();
        sumaCantCaja = 0;
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
            sumaCantCaja = item.getCantPaquete() + sumaCantCaja;
        }
    }

    public void createPDF() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        listaEmpaqueHasCaja = getListaEmpaqueHasCajaFacade().consultarItemListaEmpaque(listaEmpaqueActual);
        listaCaja = getCajaFacade().cajaListaEmpaque(listaEmpaqueActual);
        try {
            String url;
            if (listaEmpaqueActual.getTipoListaEmpaque().equals("NACIONAL")) {
                url = "http" + "://" + "192.168.160.154" + ":" + "8080" + "/intranet" + "/faces/administrador/modListaEmpaque/listaEmpaque/formatoNacional.xhtml;jsessionid=" + session.getId() + "?pdf=true";
            } else {
                url = "http" + "://" + "192.168.160.154" + ":" + "8080" + "/intranet" + "/faces/administrador/modListaEmpaque/listaEmpaque/formatoInternacional.xhtml;jsessionid=" + session.getId() + "?pdf=true";
            }
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(new URL(url).toString());
            renderer.layout();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"print-file.pdf\"");
            OutputStream outputStream = response.getOutputStream();
            renderer.createPDF(outputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        facesContext.responseComplete();
    }

    //Obtener factura
    public void organizarConsultaFactura() {
        int contar = 0;
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCajaFacade.consultarItemListaEmpaque(listaEmpaqueActual)) {
            contar++;
            if (contar == 1) {
                consultaFacturaIdMovimientos = "t470.f470_rowid_pv_movto = " + String.valueOf(item.getIdMovimiento());
            } else {
                consultaFacturaIdMovimientos = consultaFacturaIdMovimientos + " or t470.f470_rowid_pv_movto = " + String.valueOf(item.getIdMovimiento());
            }
        }
    }

    public void consultarFactura() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "";
        listaEmpaqueActual.setCodFactura("");
        if (listaEmpaqueActual.getTipoListaEmpaque().equals("NACIONAL")) {
            //Si es nacional consulta remisiones
            query = "EXEC sp_obtener_remision_lista_empaque " + listaEmpaqueActual.getCodigoLista();
        } else {
            //Si es internacional consulta facturas
            query = "SELECT top 1 t350.f350_id_tipo_docto +'-'+cast(t350.f350_consec_docto as varchar(10)) factura FROM [UnoEE].[dbo].[t470_cm_movto_invent] t470 inner join [UnoEE].[dbo].t350_co_docto_contable t350 on t350.f350_rowid = t470.f470_rowid_docto_fact inner join [UnoEE].[dbo].t461_cm_docto_factura_venta t461 on t461.f461_rowid_docto = t350.f350_rowid where t461.f461_notas like '%" + listaEmpaqueActual.getCodigoLista() + "%'  order by t470.f470_ts desc";
        }
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                listaEmpaqueActual.setCodFactura(rs.getString("factura") + " - " + listaEmpaqueActual.getCodFactura());
            }
        } catch (SQLException e) {
            System.out.println("error " + e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public BigDecimal consultarPrecioItem(int idMovimiento) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT [f431_precio_unitario_base] FROM [UnoEE].[dbo].[t431_cm_pv_movto] where [f431_rowid] = " + idMovimiento;
        BigDecimal precionItem = new BigDecimal(0);

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                precionItem = rs.getBigDecimal("f431_precio_unitario_base");
            }
            return precionItem;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return precionItem;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void actualizarCantComprometidaBase() throws SQLException {
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCajaFacade.consultarItemListaEmpaque(listaEmpaqueActual)) {
            item.setCantComprometida(consultarCantComprometidaBase(item.getIdMovimiento()));
            listaEmpaqueHasCajaFacade.edit(item);
        }
    }

    public BigDecimal consultarCantComprometidaBase(int idMovimiento) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=UnoEE;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "SELECT [f431_cant_comprometida_base] FROM [UnoEE].[dbo].[t431_cm_pv_movto] where [f431_rowid] = " + idMovimiento;
        BigDecimal cantComprometida = new BigDecimal(0);
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                cantComprometida = rs.getBigDecimal("f431_cant_comprometida_base");
            }
            return cantComprometida;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return cantComprometida;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public BigDecimal consultarPrecioListaEmpaque(int idListaEmpaque) throws SQLException {
        String connectionUrl = "jdbc:sqlserver://192.168.160.153\\EKACORP;databaseName=BD_Eureka;user=bd_eureka;password=Desarrollos478#";
        Connection con = DriverManager.getConnection(connectionUrl);
        Statement stmt = null;
        String query = "exec [sp_precio_lista_empaque]" + idListaEmpaque;
        BigDecimal precioLista = new BigDecimal(0);
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                precioLista = rs.getBigDecimal("precioLista");
            }
            return precioLista;
        } catch (SQLException e) {
            System.out.println("error " + e);
            return precioLista;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public String sincronizarDescPais() {
        for (T022ListaEmpaqueHasCaja item : listaEmpaqueHasCaja) {
            item.setDescPais(listaEmpaqueHasCajaFacade.obtenerDescItemPais(getPaisCliente(), item.getCodigoItem()));
            if (item.getDescPais().equals("")) {
                item.setDescPais(item.getDescripcion());
            }
            listaEmpaqueHasCajaFacade.edit(item);
        }
        return "reporteItems";
    }

    public String obtenerInfoLabel() {
        String textoLabel = "<div style=\"width: 4.8mm; margin: 10px 0 0 0;padding:0; font-size:8px; line-height: 9pt; font-weight: lighter;\"><p><strong>Cod: </strong>" + listaEmpaqueActual.getCodigoLista() + "     -  <strong>Caja:</strong> " + "<span style=\"font-size:10px !important\">" + cajaActual.getNumeroCaja() + "</span>";
        if (!isTipoListaEmpaqueNacional()) {
            textoLabel = textoLabel + "<br></br><strong>OC(s):</strong> " + cajaActual.getOc();
        } else {
            textoLabel = textoLabel + "<br></br><strong>Pdo(s):</strong> " + cajaActual.getPedidos();
        }
        textoLabel = textoLabel + "<br></br><strong>Peso neto:</strong> " + cajaActual.getPesoNeto().setScale(1, BigDecimal.ROUND_DOWN) + "<span style=\"font-size:6px !important\">kg</span> <br></br> <strong>Peso bruto:</strong> " + cajaActual.getPesoBruto().setScale(1, BigDecimal.ROUND_DOWN) + "<span style=\"font-size:6px !important\">kg</span></p>";
        return textoLabel;
    }

    public void imprimirLabel() throws PrinterException, IOException, Exception {
        T0231ConfigImpresoraLabel configImpresora = null;
        int contarEquipoEncontrado = 0;
        for (T0231ConfigImpresoraLabel item : getConfigImpresoraFacade().obtenerEquipoAsignado(obtenerNombreEquipoHost())) {
            if (contarEquipoEncontrado == 0) {
                contarEquipoEncontrado = contarEquipoEncontrado + 1;
                configImpresora = item;
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al encontrar asignación del equipo", "Es posible que el computador este asignado a varias impresoras");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        }
        if (contarEquipoEncontrado == 1) {
            PrintService myPrintService = seleccionarImpresora(configImpresora.getNombreImpresora());
            generarLabel(myPrintService);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error encontrar asignación del equipo", "Es posible que el computador no este creado en la asignación de impresoras");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    private static PrintService seleccionarImpresora(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }

    public void createPDFLabel() throws FileNotFoundException, DocumentException, IOException {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        document.setMargins(0, 0, 0, 0);
        PdfWriter.getInstance(document, new FileOutputStream(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.pdf")));
        document.open();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        String str = obtenerInfoLabel();
        htmlWorker.parse(new StringReader(str));
        document.close();
    }

    public void prepareImprimirLabel() throws IOException, Exception {
        createPDFLabel();
        imprimirLabel();
    }

    public String obtenerNombreEquipoHost() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            String remoteAddress = request.getRemoteAddr();
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            InetAddress inetAddress = InetAddress.getByName(remoteAddress);
            ip = inetAddress.getHostName();
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener host", "No se encontro host del equipo " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }
    }

    public void generarLabel(PrintService myPrintService) throws IOException, Exception {
        try {
            PDDocument document = PDDocument.load(new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/plano/text1.pdf")));
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.setPrintService(myPrintService);
            job.print();
        } catch (PrinterException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al generar pdf label", "Error en la generación pdf label " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

}
