package frsf.cidisi.faia.examples.situationcalculus.pacman;

import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.situationcalculus.SituationCalculusPerception;
import frsf.cidisi.faia.exceptions.PrologConnectorException;
import frsf.cidisi.faia.agent.ActionFactory;
import frsf.cidisi.faia.agent.situationcalculus.KnowledgeBase;

import java.util.Hashtable;

/**
 * Esta clase representa el estado del pacman, que en su versión lógica,
 * vendría a ser la base de conocimientos.
 * A esta clase se le realizan consultas sobre el estado del agente, y
 * se le informan de las percepciones enviadas desde el simulador, etc.
 */
public class EstadoPacmanLogico extends KnowledgeBase {
    
	/**
	 * Se le pasa al constructor de la clase padre el archivo que estamos
	 * utilizando para escribir nuestras sentencias prolog.
	 * Luego se inicializa el estado del agente.
	 * @throws PrologConnectorException
	 */
    public EstadoPacmanLogico() throws PrologConnectorException {
        super("pacman_logico.pl");

        this.initState();
    }
    
    /**
     * Este método es utilizado por el agente, dentro de 'see', para informar
     * sobre una percepción enviada por el simulador. Se traduce a una llamada
     * al método 'tell'.
     */
    @Override
    public void updateState(Perception perception) {
        this.tell((SituationCalculusPerception)perception);
    }
    
    /**
     * Imprime una representación de la base de conocimiento del agente por
     * consola.
     */
    @Override
    public String toString() {
        String str = "";

        str = str + " posicion=\"(" + this.getFila() + "," + "" + this.getColumna() + ")\"";
        str = str + " energia=\"" + this.getEnergia() + "\"\n";

        str = str + "mundo=\"[ \n";
        for (int fil = 0; fil < this.getTamanioMundo(); fil++) {
            str = str + "[ ";
            for (int col = 0; col < this.getTamanioMundo(); col++) {

                if (this.getFila() == fil && this.getColumna() == col)
                    str = str + "P" + " ";
                else
                	str = str + this.convertirCelda(this.getEstadoPosicion(fil, col));
            }
            str = str + " ]\n";
        }
        str = str + " ]\"";

        return str;
    }
    
    /**
     * Aquí se indica cuál es el objeto CalculusActionFactory definido
     * por el usuario. Ver comentarios en la clase CalculusAccionFactory.
     */
    @Override
    public ActionFactory getActionFactory() {
        return CalculusAccionFactory.getInstance();
    }

    /**
     * Este método debe devolver el string que se utiliza en el archivo
     * prolog para representar la mejor acción. En este proyecto,
     * 'pacman_logico', se lo utiliza por ejemplo en esta línea:
     * 
     *   mejorAccion(noAccion,S):-cumplioObjetivo(S),!.
     *   
     * Esto es necesario ya que la base de conocimiento realiza consultas
     * dependiendo de los nombres de los predicados utilizadas en el
     * archivo prolog.
     */
    @Override
    public String getBestActionPredicate() {
        return "mejorAccion";
    }
    
    /**
     * Tiene un objetivo similar al método 'getBestActionPredicate'.
     */
    @Override
    public String getExecutedActionPredicate() {
        return "accionEjecutada";
    }
    
    /**
     * Aquí simplemente se agrega conocimiento inicial. En este caso,
     * que la situación actual es 0, que estoy en la posición 1,2, y
     * que tengo 50 puntos de energía.
     */
    @Override
    public void initState() {
        this.addKnowledge("situacionActual(0)");
        this.addKnowledge("posicion(1,2,0)");
        this.addKnowledge("energia(50,0)");
    }
    
    // Estos métodos son internos de la clase EstadoPacman.
    public int[] getPosicion() {
        // TODO: Aca habria que hacer una consulta Prolog
        String consultaPosicion = "posicion(X,Y," + this.getSituation() + ")";

        Hashtable[] resultado = this.query(consultaPosicion);

        int x = Integer.parseInt(resultado[0].get("X").toString());
        int y = Integer.parseInt(resultado[0].get("Y").toString());

        return new int[]{x, y};
    }
    
    public int getFila() {
        return this.getPosicion()[0];
    }

    public int getColumna() {
        return this.getPosicion()[1];
    }

    public int getEnergia() {
        String consultaPosicion = "energia(E," + this.getSituation() + ")";

        Hashtable[] resultado = this.query(consultaPosicion);

        int energia = Integer.parseInt(resultado[0].get("E").toString());

        return energia;
    }

    public boolean todoConocido() {
        // TODO: Aca habria que hacer una consulta Prolog
        return true;
    }

    public int getCeldasDesconocidas() {
        // TODO: Aca habria que hacer una consulta Prolog
        return 0;
    }

    public int getComidaRestante() {
        // TODO: Aca habria que hacer una consulta Prolog
        return 0;
    }

    public boolean noHayMasComida() {
        // TODO: Aca habria que hacer una consulta Prolog
        return false;
    }

    private int getTamanioMundo() {
        return 4;
    }
    
    private String convertirCelda(int p) {
    	String r = "";
    	
    	if (p == PercepcionPacmanLogico.PERCEPCION_COMIDA)
    		r = "C";
    	else if (p == PercepcionPacmanLogico.PERCEPCION_ENEMIGO)
    		r = "E";
    	else if (p == PercepcionPacmanLogico.PERCEPCION_VACIO)
    		r = " ";
    	else if (p == PercepcionPacmanLogico.PERCEPCION_DESCONOCIDO)
    		r = "*";
    	
    	return r + " ";
    }

    private int getEstadoPosicion(int fila, int col) {

        // Veo si conozco el estado de la celda
        String consultaEstadoCelda = "conoce(" + fila + "," +
                col + "," + this.getSituation() + ")";

        if (!this.queryHasSolution(consultaEstadoCelda)) {
            return PercepcionPacmanLogico.PERCEPCION_DESCONOCIDO;        // Veo si hay un enemigo
        }
        consultaEstadoCelda = "enemigo(" + fila + "," +
                col + "," + this.getSituation() + ")";

        if (this.queryHasSolution(consultaEstadoCelda)) {
            return PercepcionPacmanLogico.PERCEPCION_ENEMIGO;        // Veo si hay comida
        }
        consultaEstadoCelda = "comida(" + fila + "," +
                col + "," + this.getSituation() + ")";

        if (this.queryHasSolution(consultaEstadoCelda)) {
            return PercepcionPacmanLogico.PERCEPCION_COMIDA;        // Veo si esta vacia
        }
        consultaEstadoCelda = "vacia(" + fila + "," +
                col + "," + this.getSituation() + ")";

        if (this.queryHasSolution(consultaEstadoCelda)) {
            return PercepcionPacmanLogico.PERCEPCION_VACIO;        // TODO: No deberia llegar aca nunca
        }
        return 9;
    }
}
