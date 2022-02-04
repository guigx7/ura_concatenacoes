package flow.subflow.Data;

import java.util.ArrayList;

import com.avaya.ade.common.utils.StringUtils;
import com.avaya.sce.runtimecommon.ITraceInfo;
import com.avaya.sce.runtimecommon.IVariable;
import com.avaya.sce.runtimecommon.IVariableField;

import english.phrasesets.Concatenacoes;
import flow.IProjectVariables;

/**
 * A basic servlet which allows a user to define their code, generate
 * any output, and to select where to transition to next.
 * Last generated by Orchestration Designer at: 28 DE JANEIRO DE 2022 15H1MIN41S BRT
 */
public class SvlConcatenacaoData extends com.avaya.sce.runtime.BasicServlet {

	private ITraceInfo traceOutput;
	//{{START:CLASS:FIELDS
	//}}END:CLASS:FIELDS

	/**
	 * Default constructor
	 * Last generated by Orchestration Designer at: 28 DE JANEIRO DE 2022 15H1MIN41S BRT
	 */
	public SvlConcatenacaoData() {
		//{{START:CLASS:CONSTRUCTOR
		super();
		//}}END:CLASS:CONSTRUCTOR
	}

	/**
	 * This method allows for custom integration with other Java components.
	 * You may use Java for sophisticated logic or to integrate with custom
	 * connectors (i.e. JMS, custom web services, sockets, XML, JAXB, etc.)
	 *
	 * Any custom code added here should work as efficiently as possible to prevent delays.
	 * It's important to design your callflow so that the voice browser (Experienve Portal/IR)
	 * is not waiting too long for a response as this can lead to a poor caller experience.
	 * Additionally, if the response to the client voice browser exceeds the configured
	 * timeout, the platform may throw an "error.badfetch". 
	 *
	 * Using this method, you have access to all session variables through the 
	 * SCESession object.
	 *
	 * The code generator will *** NOT *** overwrite this method in the future.
	 * Last generated by Orchestration Designer at: 28 DE JANEIRO DE 2022 15H1MIN41S BRT
	 */
	public void servletImplementation(com.avaya.sce.runtimecommon.SCESession mySession) {

		// TODO: Add your code here!
		
		String phraseset = "Concatenacoes:";
		String valor = "R$149,85";
		String vencimento = "27 de novembro";
				
		
		
		String[] partesData = vencimento.split(" ");
				
		int dia = Integer.valueOf(partesData[0]);
		String mes = partesData[2].substring(0, 1).toUpperCase() + partesData[2].substring(1);
		
		String audioVencimento = (dia + "_" + mes + "_F");
		
		IVariableField concatenacao = mySession.getVariableField( IProjectVariables.CONCATENACAO);
		concatenacao.setValue("Concatenacoes:" + audioVencimento);
		
		

	}

	
		
		
		

	/**
	 * Builds the list of branches that are defined for this servlet object.
	 * This list is built automatically by defining Goto nodes in the call flow editor.
	 * It is the programmer's responsibilty to provide at least one enabled Goto.<BR>
	 *
	 * The user should override updateBranches() to determine which Goto that the
	 * framework will activate.  If there is not at least one enabled Goto item, 
	 * the framework will throw a runtime exception.<BR>
	 *
	 * This method is generated automatically and changes to it may
	 * be overwritten next time code is generated.  To modify the list
	 * of branches for the flow item, override:
	 *     <code>updateBranches(Collection branches, SCESession mySession)</code>
	 *
	 * @return a Collection of <code>com.avaya.sce.runtime.Goto</code>
	 * objects that will be evaluated at runtime.  If there are no gotos
	 * defined in the Servlet node, then this returns null.
	 * Last generated by Orchestration Designer at: 2 DE FEVEREIRO DE 2022 16H32MIN26S BRT
	 */
	public java.util.Collection getBranches(com.avaya.sce.runtimecommon.SCESession mySession) {
		java.util.List list = null;
		com.avaya.sce.runtime.Goto aGoto = null;
		list = new java.util.ArrayList(1);

		aGoto = new com.avaya.sce.runtime.Goto("Data-DfnConcatenacaoData", 0, true, "Default");
		list.add(aGoto);

		return list;
	}
	
	 public static void main(String[] args) {
		try {
			String valor = "R$149,85";
			String vencimento = "27 de novembro";
			
			String[] partesValor = valor.replace("R$", "").split(",");

			String reais = partesValor[0];
			System.out.println( "reais "+ reais);

			String centavos = partesValor[1];
			System.out.println( "centavos "+ centavos);
			
			ArrayList<String> lista = new ArrayList<String>();

//			boolean milhar = reais.length()==4;
//			boolean centena = reais.length()==3;
//			boolean dezena = reais.length()==2;
//			boolean unidade = reais.length()==1;

			switch( reais.length()) {
				case 4:
					
					break;
				case 3:

					if( reais.equalsIgnoreCase("100")) {}
					else {
						char digitoCentena = reais.charAt( 0);
						System.out.println( "digitoCentena "+ digitoCentena);

						String restante =  reais.substring( 1);
						System.out.println( "restante "+ restante);

						lista.add( restante +"Reais_NF.wav");
					}

					break;
				case 2:
					
					break;
				case 1:
					
					break;
			}
			
			int intCentavos = Integer.valueOf( centavos);
			System.out.println( intCentavos);

			lista.add( String.format( "e%dcentavos_F.wav", intCentavos));
			
			System.out.println( "lista: "+ lista);
			
			//data
			
			String[] partesData = vencimento.split(" ");
			
			System.out.println(partesData[0]);
			System.out.println(partesData[1]);
			System.out.println(partesData[2]);
			
			int dia = Integer.valueOf(partesData[0]);
			String mes = partesData[2].substring(0, 1).toUpperCase() + partesData[2].substring(1);
						
			System.out.println(dia + " " + mes);
			
			String audioVencimento = (dia + "_" + mes + "_F");
			
			System.out.println(lista);
			System.out.println(audioVencimento);
			
			//IVariable concatData = mySession.getVariable( IProjectVariables.CONCATENACAO);
			//concatData.getSimpleVariable().setValue(audioVencimento);
			
			//System.out.println("ccdt" + concatData);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}
