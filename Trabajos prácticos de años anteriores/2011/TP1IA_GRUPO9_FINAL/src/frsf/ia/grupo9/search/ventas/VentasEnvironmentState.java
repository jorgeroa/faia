package frsf.ia.grupo9.search.ventas;

import static frsf.ia.grupo9.search.ventas.Constantes.*;

import java.util.ArrayList;
import java.util.List;

import frsf.cidisi.faia.state.EnvironmentState;

/**
 * Clase que representa el estado del ambiente
 */
public class VentasEnvironmentState extends EnvironmentState {

	/**
	 * @uml.property  name="estados"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="frsf.ia.grupo9.search.ventas.EstadoProceso"
	 */
	private List<EstadoProceso> estados = new ArrayList<EstadoProceso>();
	/**
	 * @uml.property  name="estadoActual"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso estadoActual;
	/**
	 * @uml.property  name="tiempoUltimaActividad"
	 */
	private float tiempoUltimaActividad;
	
	/**
	 * @uml.property  name="tiempoEnvioForecastRequest"
	 */
	private float tiempoEnvioForecastRequest;
	/**
	 * @uml.property  name="tiempoEnvioInform"
	 */
	private float tiempoEnvioInform;
	/**
	 * @uml.property  name="ciclosNegociacion"
	 */
	private int ciclosNegociacion;
	
    public VentasEnvironmentState() {
    	this.initState();
    }

	@Override
	public void initState() {
		estados.add(new EstadoProceso(ESTADO_INICIAL));
		estados.add(new EstadoProceso(GENERAR_SOLICITUD_PRONOSTICO_DEMANDA, 10, 15));
		estados.add(new EstadoProceso(GENERAR_SOLICITUD_PRONOSTICO_DEMANDA, FORECAST_REQUEST,10, 15));
		estados.add(new EstadoProceso(ENVIAR_FORECAST_REQUEST, 1, 3));
		estados.add(new EstadoProceso(ENVIAR_FORECAST_REQUEST, AGREE_FORECAST_REQUEST_RESPONSE, 2, 3));
		estados.add(new EstadoProceso(ENVIAR_FORECAST_REQUEST, REFUSE_FORECAST_REQUEST_RESPONSE, 2, 4));
		estados.add(new EstadoProceso(PROCESO_FINALIZADO_FRACASO,FAIL));
		estados.add(new EstadoProceso(PROCESO_FINALIZADO_EXITO,SUCCESS));
		estados.add(new EstadoProceso(GENERAR_PRONOSTICO_VENTAS_POS,POS_FORECAST, 12, 96));
		estados.add(new EstadoProceso(GENERAR_PLAN_EVENTOS_PROGRAMADOS,PLANNED_EVENTS, 30, 150));
		estados.add(new EstadoProceso(ENVIAR_INFORME_POSFORECAST,PROPOSE_DEMAND_FORECAST_1, 1, 2));
		estados.add(new EstadoProceso(ENVIAR_PLAN_EVENTOS_PROGRAMADOS,PROPOSE_DEMAND_FORECAST_1, 2, 4));
		estados.add(new EstadoProceso(EVALUAR_PRONOSTICO_DEMANDA,DEMAND_FORECAST_RESPONSE_YES, 10, 20));
		estados.add(new EstadoProceso(EVALUAR_PRONOSTICO_DEMANDA,DEMAND_FORECAST_RESPONSE_NO, 10, 20));
		estados.add(new EstadoProceso(ACTUALIZAR_PRONOSTICO, UPDATED_DEMAND_FORECAST, 2, 4));
		estados.add(new EstadoProceso(ENVIAR_PROPUESTA_PRONOSTICO_ACTUALIZADO, PROPOSE_DEMAND_FORECAST_2, 1, 2));
		estados.add(new EstadoProceso(ENVIAR_PROPUESTA_PRONOSTICO_ACTUALIZADO, INFORM_ISSUES, 1, 2));
		estados.add(new EstadoProceso(ENVIAR_ACEPTACION_PROPUESTA,SUCCESS, 1, 3));
		estados.add(new EstadoProceso(ENVIAR_CANCELACION_DEMANDA_FORECAST));
		estados.add(new EstadoProceso(CANCELAR_DEMAND_FORECAST));
		this.estadoActual = estados.get(0);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		VentasEnvironmentState nuevoEstado = new VentasEnvironmentState();
		if (this.estados != null) {
			List<EstadoProceso> nuevosEstados = new ArrayList<EstadoProceso>();
			for (EstadoProceso e : this.estados) {
				nuevosEstados.add((EstadoProceso) e.clone());
			}
			nuevoEstado.setEstados(nuevosEstados);
		}
		nuevoEstado.setEstadoActual((EstadoProceso) this.estadoActual.clone());
		nuevoEstado.setTiempoUltimaActividad(this.tiempoUltimaActividad);
		nuevoEstado.setCiclosNegociacion(this.ciclosNegociacion);
		nuevoEstado.setTiempoEnvioForecastRequest(this.tiempoEnvioForecastRequest);
		nuevoEstado.setTiempoEnvioInform(this.tiempoEnvioInform);
		return nuevoEstado;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("VentasEnvironmentState: [");
		sb.append("EstadoProceso: ");
		sb.append("[");
		sb.append("Actividad: ");
		sb.append(this.estadoActual.getActividad());
		sb.append("Resultado: ");
		sb.append(this.estadoActual.getResultado());
		sb.append("]");
		sb.append(",ciclosNegociacion: ");
		sb.append(this.ciclosNegociacion);
		sb.append(",tiempoUltimaActividad: ");
		sb.append(this.tiempoUltimaActividad);
		sb.append(",tiempoEnvioForecastRequest: ");
		sb.append(this.tiempoEnvioForecastRequest);
		sb.append(",tiempoEnvioInform: ");
		sb.append(this.tiempoEnvioInform);
		sb.append(",ciclosNegociacion: ");
		sb.append(this.ciclosNegociacion);
		sb.append("]");
		return sb.toString();
	}

	public List<EstadoProceso> getEstados() {
		return estados;
	}

	public void setEstados(List<EstadoProceso> estados) {
		this.estados = estados;
	}

	/**
	 * @return
	 * @uml.property  name="estadoActual"
	 */
	public EstadoProceso getEstadoActual() {
		return estadoActual;
	}

	/**
	 * @param estadoActual
	 * @uml.property  name="estadoActual"
	 */
	public void setEstadoActual(EstadoProceso estadoActual) {
		this.estadoActual = estadoActual;
	}

	/**
	 * @return
	 * @uml.property  name="tiempoUltimaActividad"
	 */
	public float getTiempoUltimaActividad() {
		return tiempoUltimaActividad;
	}

	/**
	 * @param tiempoUltimaActividad
	 * @uml.property  name="tiempoUltimaActividad"
	 */
	public void setTiempoUltimaActividad(float tiempoUltimaActividad) {
		this.tiempoUltimaActividad = tiempoUltimaActividad;
	}

	/**
	 * @return
	 * @uml.property  name="ciclosNegociacion"
	 */
	public int getCiclosNegociacion() {
		return ciclosNegociacion;
	}

	/**
	 * @param ciclosNegociacion
	 * @uml.property  name="ciclosNegociacion"
	 */
	public void setCiclosNegociacion(int ciclosNegociacion) {
		this.ciclosNegociacion = ciclosNegociacion;
	}
	
	/**
	 * @return
	 * @uml.property  name="tiempoEnvioForecastRequest"
	 */
	public float getTiempoEnvioForecastRequest() {
		return tiempoEnvioForecastRequest;
	}

	/**
	 * @param tiempoEnvioForecastRequest
	 * @uml.property  name="tiempoEnvioForecastRequest"
	 */
	public void setTiempoEnvioForecastRequest(float tiempoEnvioForecastRequest) {
		this.tiempoEnvioForecastRequest = tiempoEnvioForecastRequest;
	}

	/**
	 * @return
	 * @uml.property  name="tiempoEnvioInform"
	 */
	public float getTiempoEnvioInform() {
		return tiempoEnvioInform;
	}

	/**
	 * @param tiempoEnvioInform
	 * @uml.property  name="tiempoEnvioInform"
	 */
	public void setTiempoEnvioInform(float tiempoEnvioInform) {
		this.tiempoEnvioInform = tiempoEnvioInform;
	}

	public void setNuevoEstado(EstadoProceso nuevoEstado) {
		int index = estados.indexOf(nuevoEstado);
		if (index == -1) {
			throw new IllegalStateException("Estado desconocido por el ambiente: " + nuevoEstado);
		}
		estadoActual = estados.get(index);
		tiempoUltimaActividad = estadoActual.tiempoAleatorio();
	}

}
