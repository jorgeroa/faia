package frsf.ia.grupo9.search.ventas;

import java.util.Random;

public class EstadoProceso implements Cloneable {

    public static final Random RND = new Random(System.currentTimeMillis());

	/**
	 * @uml.property  name="actividad"
	 */
	private String actividad;
	
	/**
	 * @uml.property  name="resultado"
	 */
	private String resultado;
	
	/**
	 * @uml.property  name="tiempoMinimo"
	 */
	private int tiempoMinimo;
	
	/**
	 * @uml.property  name="tiempoMaximo"
	 */
	private int tiempoMaximo;
	
	/**
	 * @uml.property  name="tiempoActividad"
	 */
	private float tiempoActividad;
	
	public EstadoProceso() {}
	
	public EstadoProceso(String actividad, String resultado, int tiempoMinimo,
			int tiempoMaximo) {
		super();
		this.actividad = actividad;
		this.resultado = resultado;
		this.tiempoMinimo = tiempoMinimo;
		this.tiempoMaximo = tiempoMaximo;
		this.tiempoActividad = this.tiempoAleatorio();
	}

	public EstadoProceso(String actividad, int tiempoMinimo,
			int tiempoMaximo) {
		super();
		this.actividad = actividad;
		this.tiempoMinimo = tiempoMinimo;
		this.tiempoMaximo = tiempoMaximo;
	}

	public EstadoProceso(String actividad, String resultado) {
		super();
		this.actividad = actividad;
		this.resultado = resultado;
	}

	public EstadoProceso(String actividad) {
		super();
		this.actividad = actividad;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new EstadoProceso(this.actividad, this.resultado,
			this.tiempoMinimo, this.tiempoMaximo);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof EstadoProceso)) return false;
		EstadoProceso other = (EstadoProceso) obj;
		return this.actividad != null && 
			this.actividad.equals(other.actividad) &&
			((this.resultado == null && other.resultado == null) ||
			 (this.resultado != null &&
			  this.resultado.equals(other.resultado)));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("EstadoProceso: [");
		builder.append("actividad: ");
		builder.append(this.actividad);
		builder.append(",resultado: ");
		builder.append(this.resultado);
		builder.append(",tiempoMinimo: ");
		builder.append(this.tiempoMinimo);
		builder.append(",tiempoMaximo: ");
		builder.append(this.tiempoMaximo);
		builder.append("]");
		return builder.toString();
	}
	
	public float tiempoPromedio() {
		return (this.tiempoMinimo + this.tiempoMaximo) / 2;
	}
	
	public float tiempoAleatorio() {
		return RND.nextInt(tiempoMaximo - tiempoMinimo + 1) + tiempoMinimo;
	}

	/**
	 * @return
	 * @uml.property  name="actividad"
	 */
	public String getActividad() {
		return actividad;
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
	 * @uml.property  name="tiempoMinimo"
	 */
	public int getTiempoMinimo() {
		return tiempoMinimo;
	}

	/**
	 * @return
	 * @uml.property  name="tiempoMaximo"
	 */
	public int getTiempoMaximo() {
		return tiempoMaximo;
	}

	/**
	 * @param actividad
	 * @uml.property  name="actividad"
	 */
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	/**
	 * @param resultado
	 * @uml.property  name="resultado"
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * @param tiempoMinimo
	 * @uml.property  name="tiempoMinimo"
	 */
	public void setTiempoMinimo(int tiempoMinimo) {
		this.tiempoMinimo = tiempoMinimo;
	}

	/**
	 * @param tiempoMaximo
	 * @uml.property  name="tiempoMaximo"
	 */
	public void setTiempoMaximo(int tiempoMaximo) {
		this.tiempoMaximo = tiempoMaximo;
	}
	
	public float tiempoActividad(){
		return this.tiempoActividad;
	}

}
