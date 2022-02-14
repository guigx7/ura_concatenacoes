package flow.subflow.FPD_Parcelamento;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import com.avaya.sce.runtimecommon.ITraceInfo;
import com.avaya.sce.runtimecommon.IVariable;
import com.avaya.sce.runtimecommon.SCESession;

import flow.IProjectVariables;

/**
 * A basic servlet which allows a user to define their code, generate
 * any output, and to select where to transition to next.
 * Last generated by Orchestration Designer at: 27 DE JANEIRO DE 2022 16:53:18 BRT
 */
public class SvlFPD_Parcelamento extends com.avaya.sce.runtime.BasicServlet {

	private ITraceInfo traceOutput;
	//{{START:CLASS:FIELDS
	//}}END:CLASS:FIELDS

	/**
	 * Default constructor
	 * Last generated by Orchestration Designer at: 27 DE JANEIRO DE 2022 16:53:18 BRT
	 */
	public SvlFPD_Parcelamento() {
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
	 * Last generated by Orchestration Designer at: 27 DE JANEIRO DE 2022 16:53:18 BRT
	 */
	public void servletImplementation(SCESession mySession) {
		traceOutput = mySession.getTraceOutput();
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_INFO, "##################################################");

		String url = mySession.getRequest().getLocalAddr();
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "url "+ url);
		
		String porta = String.valueOf(mySession.getRequest().getLocalPort());
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "porta "+ porta);
		
		String fonoteca = String.format("http://%s:%s/frases/", url, porta);
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "fonoteca "+ fonoteca);
		
		String tomcat = mySession.getAbsoluteTempDirPath();
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "tomcat "+ tomcat);
		
		String tomcat2 = mySession.getRequestCopyURL();
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "tomcat2 "+ tomcat2);
		
		String phraseset = "Concatenacoes:";
		String valor = "R$149,85";
		String vencimento = "27 de novembro";
		String data = "";
		
		
		String[] partesValor = valor.replace("R$", "").split(",");

		String reais = partesValor[0];
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "reais "+ reais);

		String centavos = partesValor[1];
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "centavos "+ centavos);
		
		ArrayList<String> lista = new ArrayList<String>();
		
		lista.add( fonoteca+ "Fpd_a.wav");
		
//		boolean milhar = reais.length()==4;
//		boolean centena = reais.length()==3;
//		boolean dezena = reais.length()==2;
//		boolean unidade = reais.length()==1;

		switch( reais.length()) {
			case 4:
				
				break;
			case 3:

				if( reais.equalsIgnoreCase("100")) {}
				else {
					char digitoCentena = reais.charAt( 0);
					traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "digitoCentena "+ digitoCentena);

					String fraseCentena = fraseCentena( digitoCentena);
					traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "fraseCentena "+ fraseCentena);
					lista.add( fonoteca+ fraseCentena);

					String restante =  reais.substring( 1);
					traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "restante "+ restante);

					lista.add( fonoteca+ restante +"Reais_NF.wav");
				}

				break;
			case 2:
				
				break;
			case 1:
				
				break;
		}
		
		int intCentavos = Integer.valueOf( centavos);
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, ""+intCentavos);

		lista.add( String.format( "%se%dcentavos_F.wav", fonoteca, intCentavos));

		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "lista: "+ lista);
		
		
		lista.add( fonoteca+ "Fpd_parcelamento_b.wav");
		
		//data

		String[] partesData = vencimento.split(" ");
		
		int dia = Integer.valueOf(partesData[0]);
		String mes = partesData[2].substring(0, 1).toUpperCase() + partesData[2].substring(1);
		
		String audioVencimento = (dia + "_" + mes + "_F.wav");
		
		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_DEBUG, "audioVencimento "+ audioVencimento);
		lista.add( fonoteca+ audioVencimento);
		
		//data
		
		lista.add( fonoteca+ "Fpd_parcelamento_c.wav");
		
		IVariable concatenacao = mySession.getVariable( IProjectVariables.CONCATENACAO);
		concatenacao.addCollection();
		
		for( String frase : lista) {
			concatenacao.getSimpleVariable().setValue( frase);
			concatenacao.getCollection().append();
		}
		
		concatenacao.getCollection().reset();
		concatenacao.getCollection().next();

		traceOutput.writeln( ITraceInfo.TRACE_LEVEL_INFO, "##################################################");
	}

	public static String fraseCentena( char digito) {
		String retorno = "";

		switch( digito) {
			case '1':
				retorno = "100e_NF.wav";
				break;
			case '2':
				retorno = "200e_NF.wav";
				break;
			case '3':
				retorno = "300e_NF.wav";
				break;
			case '4':
				retorno = "400e_NF.wav";
				break;
			case '5':
				retorno = "500e_NF.wav";
				break;
			case '6':
				retorno = "600e_NF.wav";
				break;
			case '7':
				retorno = "700e_NF.wav";
				break;
			case '8':
				retorno = "800e_NF.wav";
				break;
			case '9':
				retorno = "900e_NF.wav";
				break;
		}

//		System.out.println("retorno "+ retorno);
		
		return retorno;
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
	 * Last generated by Orchestration Designer at: 4 DE FEVEREIRO DE 2022 14H11MIN30S BRT
	 */
	public java.util.Collection getBranches(com.avaya.sce.runtimecommon.SCESession mySession) {
		java.util.List list = null;
		com.avaya.sce.runtime.Goto aGoto = null;
		list = new java.util.ArrayList(1);

		aGoto = new com.avaya.sce.runtime.Goto("FPD_Parcelamento-DfnConcatenacao", 0, true, "Default");
		list.add(aGoto);

		return list;
	}
	
	
	public static void main(String[] args) {
		try {
			String valor = "R$149,85";
			String vencimento = "27 de novembro";
			
			//valor
			
			String[] partesValor = valor.replace("R$", "").split(",");

			String reais = partesValor[0];
			System.out.println( "reais "+ reais);

			String centavos = partesValor[1];
			System.out.println( "centavos "+ centavos);
			
			ArrayList<String> lista = new ArrayList<String>();

			lista.add("Fpd_parcelamento_a.wav");
			
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

						String fraseCentena = fraseCentena( digitoCentena);
						System.out.println( "fraseCentena "+ fraseCentena);
						lista.add( fraseCentena);

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
			
			//data
			
			lista.add("Fpd_parcelamento_b.wav");
			
			String[] partesData = vencimento.split(" ");
			
			int dia = Integer.valueOf(partesData[0]);
			String mes = partesData[2].substring(0, 1).toUpperCase() + partesData[2].substring(1);
			
			String audioVencimento = (dia + "_" + mes + "_F");
			lista.add(audioVencimento + ".wav");
			
			lista.add("Fpd_parcelamento_c.wav");
			
			System.out.println( "lista: "+ lista);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 1995);
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 20);
			
			System.out.println(cal.getTime());
			System.out.println(cal.get(Calendar.YEAR));
			System.out.println(cal.get(Calendar.MONDAY));
			System.out.println(cal.get(Calendar.DAY_OF_MONTH));
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.now();
			System.out.println(dtf.format(localDate));
			System.out.println(dtf2.format(localDate));
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	        java.util.Date date1 = sdf.parse("27-11-2022");
	        java.util.Date date2 =  sdf.parse("28-11-2022");

	        System.out.println("date1 : " + sdf.format(date1));
	        System.out.println("date2 : " + sdf.format(date2));

	        int result = date1.compareTo(date2);
	        System.out.println("result: " + result);

	        if (result > 0 || result == 0) {
	            System.out.println("Vai vencer");
	        }
	        else if (result < 0) {
	            System.out.println("Ja venceu");
	        }
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
}
