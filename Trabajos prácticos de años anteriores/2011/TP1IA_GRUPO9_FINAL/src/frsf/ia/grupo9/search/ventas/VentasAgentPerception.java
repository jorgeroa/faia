package frsf.ia.grupo9.search.ventas;

import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;

/**
 * Clase que representa la percepcion que recibe el agente sobre el ambiente.
 */
public class VentasAgentPerception extends Perception {

	/**
	 * @uml.property  name="actividad"
	 */
	private String actividad;
    /**
	 * @uml.property  name="resultado"
	 */
    private String resultado;
	/**
	 * @uml.property  name="tiempoEnvioForecastRequest"
	 */
	private float tiempoEnvioForecastRequest;
	/**
	 * @uml.property  name="tiempoEnvioInform"
	 */
	private float tiempoEnvioInform;
    /**
	 * @uml.property  name="tiempoUltimaActividad"
	 */
    private float tiempoUltimaActividad;
    /**
	 * @uml.property  name="ciclosNegociacion"
	 */
    private int ciclosNegociacion;

    public  VentasAgentPerception() {
    	super();
    }

    public VentasAgentPerception(Agent agent, Environment environment) {
        super(agent, environment);
    }

    /**
     * This method is used to setup the perception.
     */
    @Override
    public void initPerception(Agent agentIn, Environment environmentIn) {
        VentasEnvironmentState environmentState =
                (VentasEnvironmentState) environmentIn.getEnvironmentState();
        this.actividad = environmentState.getEstadoActual().getActividad();
        this.resultado = environmentState.getEstadoActual().getResultado();
        this.ciclosNegociacion = environmentState.getCiclosNegociacion();
        this.tiempoEnvioInform = environmentState.getTiempoEnvioInform();
        this.tiempoEnvioForecastRequest = environmentState.getTiempoEnvioForecastRequest();
        this.tiempoUltimaActividad = environmentState.getTiempoUltimaActividad();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Percepci\u00F3n: [");
        builder.append("actividad: ");
        builder.append(this.actividad);
        builder.append(",resultado: ");
        builder.append(this.resultado);
        builder.append(", tiempoEnvioForecastRequest: ");
        builder.append(this.tiempoEnvioForecastRequest);
        builder.append(", ciclosNegociacion: ");
        builder.append(this.ciclosNegociacion);
        builder.append(", tiempoEnvioInform: ");
        builder.append(this.tiempoEnvioInform);
        builder.append(", tiempoUltimaActividad: ");
        builder.append(this.tiempoUltimaActividad);
        builder.append("]");
        return builder.toString();
    }    

	/**
	 * @return
	 * @uml.property  name="actividad"
	 */
	public String getActividad() {
		return actividad;
	}

	/**
	 * @param actividad
	 * @uml.property  name="actividad"
	 */
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	/**
	 * @return
	 * @uml.property  name="resultado"
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * @return
	 * @uml.property  name="tiempoUltimaActividad"
	 */
	public float getTiempoUltimaActividad() {
		return tiempoUltimaActividad;
	}

	/**
	 * @param resultado
	 * @uml.property  name="resultado"
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
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
