package frsf.ia.grupo9.search.ventas;

import static frsf.ia.grupo9.search.ventas.Constantes.ESTADO_INICIAL;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;

/**
 * Clase que representa un estado del agente
 */
public class VentasAgentState extends SearchBasedAgentState {

	/**
	 * @uml.property  name="estadoProceso"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso estadoProceso;
	/**
	 * @uml.property  name="tiempoAcumulado"
	 */
	private float tiempoAcumulado;
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

	public VentasAgentState() {
		this.initState();
    }

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof VentasAgentState)) return false;
		VentasAgentState other = (VentasAgentState) obj;
		return this.estadoProceso != null && 
			this.estadoProceso.equals(other.estadoProceso) &&
			(this.ciclosNegociacion == other.ciclosNegociacion); // SE AGREGO CICLO DE NEGOCIACION PORQ SE PUEDE REPETIR 
																 //	LA ACTIVIDAD EN EL PROCESO DE NEGOCIO EN DISTINTOS CICLOS;
	}

	@Override
	public SearchBasedAgentState clone() {
		VentasAgentState nuevoEstado = new VentasAgentState();
		if (estadoProceso != null) {
			try {
				nuevoEstado.setEstadoProceso((EstadoProceso) this.estadoProceso.clone());
			} catch (CloneNotSupportedException e) {
				throw new IllegalStateException(e);
			}
		}
		nuevoEstado.setTiempoAcumulado(this.tiempoAcumulado);
		nuevoEstado.setCiclosNegociacion(this.ciclosNegociacion);
		nuevoEstado.setTiempoEnvioForecastRequest(this.tiempoEnvioForecastRequest);
		nuevoEstado.setTiempoEnvioInform(this.tiempoEnvioInform);
		return nuevoEstado;
	}

	@Override
	public void updateState(Perception p) {
		VentasAgentPerception perception = (VentasAgentPerception) p;
		if (perception != null) {
			this.estadoProceso.setActividad(perception.getActividad());
			this.estadoProceso.setResultado(perception.getResultado());
			this.tiempoEnvioInform = perception.getTiempoEnvioInform();
			this.tiempoEnvioForecastRequest = perception.getTiempoEnvioForecastRequest();
			this.ciclosNegociacion = perception.getCiclosNegociacion();
			this.incrementarTiempoAcumulado(perception.getTiempoUltimaActividad());
		}
	}

    public void incrementarTiempoAcumulado(float duracionActividad) {
        this.tiempoAcumulado += duracionActividad;
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("VentasAgentState: [");
		sb.append("EstadoProceso: ");
		sb.append("[");
		sb.append("Actividad: ");
		sb.append(this.estadoProceso.getActividad());
		sb.append("Resultado: ");
		sb.append(this.estadoProceso.getResultado());
		sb.append("]");
		sb.append(", tiempoAcumulado: ");
		sb.append(this.tiempoAcumulado);
		sb.append(", tiempoEnvioForecastRequest: ");
		sb.append(this.tiempoEnvioForecastRequest);
		sb.append(", tiempoEnvioInform: ");
		sb.append(this.tiempoEnvioInform);
		sb.append(", ciclosNegociacion: ");
		sb.append(this.ciclosNegociacion);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public void initState() {
		EstadoProceso estado = new EstadoProceso(ESTADO_INICIAL);
		this.estadoProceso = estado;
	}

	/**
	 * @return
	 * @uml.property  name="estadoProceso"
	 */
	public EstadoProceso getEstadoProceso() {
		return estadoProceso;
	}

	/**
	 * @param estadoProceso
	 * @uml.property  name="estadoProceso"
	 */
	public void setEstadoProceso(EstadoProceso estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	/**
	 * @return
	 * @uml.property  name="tiempoAcumulado"
	 */
	public float getTiempoAcumulado() {
		return tiempoAcumulado;
	}

	/**
	 * @param tiempoAcumulado
	 * @uml.property  name="tiempoAcumulado"
	 */
	public void setTiempoAcumulado(float tiempoAcumulado) {
		this.tiempoAcumulado = tiempoAcumulado;
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

	
}
