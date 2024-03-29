package br.ufc.storm.backend;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.axis2.AxisFault;
import org.apache.ws.axis2.FakeEndServicesStub;
import org.apache.ws.axis2.FakeEndServicesStub.AddFileResponse;
import org.apache.ws.axis2.FakeEndServicesStub.DeploycallBackResponse;
import org.apache.ws.axis2.FakeEndServicesStub.GetStatusResponse;
import org.apache.ws.axis2.FakeEndServicesStub.RemoveFileResponse;
import org.apache.ws.axis2.FakeEndServicesStub.RunFileResponse;
import org.apache.ws.axis2.FakeEndServicesStub.SetRunnableResponse;

import br.ufc.mdcc.hpcshelf.backendservices.proxy.BackEndServicesImplServiceStub;
import br.ufc.mdcc.hpcshelf.backendservices.proxy.BackEndServicesImplServiceStub.DeployContractCallback;
import br.ufc.mdcc.hpcshelf.backendservices.proxy.BackEndServicesImplServiceStub.DestroyPlatform;
import br.ufc.storm.exception.DBHandlerException;
import br.ufc.storm.exception.ShelfRuntimeException;
import br.ufc.storm.io.LogHandler;
import br.ufc.storm.jaxb.CandidateListType;
import br.ufc.storm.jaxb.ComputationalSystemType;
import br.ufc.storm.jaxb.ConcreteUnitType;
import br.ufc.storm.jaxb.ContextArgumentType;
import br.ufc.storm.jaxb.ContextContract;
import br.ufc.storm.jaxb.PlatformProfileType;
import br.ufc.storm.jaxb.UnitFileType;
import br.ufc.storm.properties.PropertiesHandler;
import br.ufc.storm.sql.ConcreteUnitHandler;
import br.ufc.storm.sql.PlatformHandler;
import br.ufc.storm.sql.SessionHandler;

public class BackendHandler {

	CandidateListType list; //List of candidates
	int candidate; //Position of actual candidate in array list
	ComputationalSystemType computationalSystem;
	int session; //Session id

	public BackendHandler(CandidateListType list) {
		this.list = list;
		candidate = 0;
	}
public static void main(String[] args) throws IOException {
		try {
//			System.out.println(BackendHandler.requestPlatformAxis("127.0.0.1", 1234,8000, 321+""));
			System.out.println(BackendHandler.requestPlatform("200.19.177.89", 1235,8000, 321+""));//200.19.177.89

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



//	public static void main(String[] args) {
//		String server = "200.19.177.94";
//		String sourceFile = "/tmp/teste.sh";
//		String targetFile = "/tmp/shelf-dir/hos.sh";
//		System.out.println(BackendHandler.getStatus(server));
////		System.out.println(sendFile(sourceFile, server, targetFile));
////		System.out.println(setRunnable(server, "chmod +x "+targetFile));
////		System.out.println(setRunnable(server, targetFile));
////		System.out.println(deleteFile(server, targetFile));
//		
//	}



	
	
//	public static boolean instantiatePlatftormOld(PlatformProfileType platform) throws ShelfRuntimeException{
//		try {
//			platform.setNetworkIpAddress(PlatformHandler.getPlatformIP(platform.getPlatformContract().getCcId()));
//		} catch (DBHandlerException e1) {
//			throw new ShelfRuntimeException(platform.getPlatformContract().getCcId()+" > No ip valid ip address", e1);
//		}
//		try{
//			if (getStatus(platform.getNetworkIpAddress()).equals("This is ok")) {
//				LogHandler.getLogger().warning("Platform Instantiated with ip: "+platform.getNetworkIpAddress());
//				return true;
//				
//			} else {
//				throw new ShelfRuntimeException("Can not instantiate platform");
//			}
//		}catch(Exception e){
//			throw new ShelfRuntimeException("Can not instantiate platform at this moment", e);
//		}
//	}

//	Método recursivo
	public static List<UnitFileType> getFiles(ContextContract cc) throws ShelfRuntimeException{
		List<UnitFileType> listOfFiles = new ArrayList<>();
		
		
		List<ConcreteUnitType> listOfMainConcreteUnits;
		
		
		try {
			listOfMainConcreteUnits = ConcreteUnitHandler.getConcreteUnits(cc.getCcId());
		} catch (DBHandlerException e1) {
			throw new ShelfRuntimeException("Can not get units", e1);
		}
		for(ConcreteUnitType cut : listOfMainConcreteUnits){
			try {
				listOfFiles.addAll(ConcreteUnitHandler.getUnitFiles(cut.getUId()));
			} catch (DBHandlerException e) {
				throw new ShelfRuntimeException("Can not get unit file", e);
			}
		}
		
		for(ContextArgumentType cat : cc.getContextArguments()){
			if (cat.getContextContract()!=null){
				List<ConcreteUnitType> listOfConcreteUnits;
				
				
				try {
					listOfConcreteUnits = ConcreteUnitHandler.getConcreteUnits(cat.getContextContract().getCcId());
				} catch (DBHandlerException e1) {
					throw new ShelfRuntimeException("Can not get units", e1);
				}
				for(ConcreteUnitType cut : listOfConcreteUnits){
					try {
						listOfFiles.addAll(ConcreteUnitHandler.getUnitFiles(cut.getUId()));
					} catch (DBHandlerException e) {
						throw new ShelfRuntimeException("Can not get unit file", e);
					}
				}
				for(ContextContract inner : cc.getInnerComponents()){
					listOfFiles.addAll(getFiles(inner));
				}
			}
			
		}
		
		return listOfFiles;
	}
	


	//	public static void main(String[] args) {
	//		String server = "200.19.177.94";
	//		String sourceFile = "/tmp/teste.sh";
	//		String targetFile = "/tmp/shelf-dir/hos.sh";
	//		System.out.println(BackendHandler.getStatus(server));
	////		System.out.println(sendFile(sourceFile, server, targetFile));
	////		System.out.println(setRunnable(server, "chmod +x "+targetFile));
	////		System.out.println(setRunnable(server, targetFile));
	////		System.out.println(deleteFile(server, targetFile));
	//		
	//	}
	
	
	/**
		 * This method tries to instantiate an platform in respective BackEnd
		 * Create VMs or reserve nodes
		 * Call Core back with URI of head node of parallel platform
		 * 
		 * @return ComputationalSystem instantiated
		 * @throws ShelfRuntimeException 
		 */
		
		public static boolean deployPlatftorm(ContextContract cc, int sessionID) throws ShelfRuntimeException{
			try {
				System.out.println("Entrou no instantiate");
				cc.getPlatform().setNetworkIpAddress(requestPlatform(PlatformHandler.getPlatformIP(cc.getPlatform().getPlatformContract().getCcId()), sessionID, PlatformHandler.getPlatformPort(cc.getPlatform().getPlatformContract().getCcId()), cc.getPlatform().getPlatformContract().getCcId()+""));
				ComputationalSystemType cst = new ComputationalSystemType();
				cst.setContextContract(cc);
				SessionHandler.setCst(sessionID, cst);
				System.out.println("----------------------------");
				System.out.println(cc.getPlatform().getNetworkIpAddress());
				LogHandler.getLogger().warning("Platform Instantiated with ip: "+cc.getPlatform().getNetworkIpAddress());
				return true;
			} catch (DBHandlerException e1) {
				throw new ShelfRuntimeException(cc.getPlatform().getPlatformContract().getCcId()+" > No ip valid ip address", e1);
			}
		}
	public static ComputationalSystemType deploy(CandidateListType clist) throws ShelfRuntimeException{
		ComputationalSystemType platform = null;
		try {
//			Find 1º available platform
			for(ContextContract cc : clist.getCandidate()){
				//Create session
				int sessionID = SessionHandler.createSession(cc.getOwnerId());//Get client id
				System.out.println("Component: "+cc.getCcName()+" Platform: "+cc.getPlatform().getPlatformContract().getCcName());
				if (deployPlatftorm(cc, sessionID)){//try instantiate platform
					
					
					//Create computational system and set network info
					ComputationalSystemType cst = new ComputationalSystemType();
//					cst.setContextContract(cc);
//					cst.setNetworkAddress(cc.getPlatform().getNetworkIpAddress());
////					erro no id do usuario
//					cst.setSession(sessionID);
//					//copiar arquivos das unidades
////					Create unit files list from computational system
//					List<UnitFileType> listOfFile = getFiles(cst.getContextContract());//Recursive method that generates a list of files to send to platform
//					cst.getFiles().addAll(listOfFile); 
//					//Send unit files to platform
//					BackendHandler.sendFiles(cst, listOfFile);
					return cst;
				}
			}			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ShelfRuntimeException("Can not instantiate component files", e);
		}
	}

	public static ComputationalSystemType deploySoftwareComponent(ComputationalSystemType cst) throws ShelfRuntimeException{
		ComputationalSystemType platform = null;
		try {
					List<UnitFileType> listOfFile = getFiles(cst.getContextContract());//Recursive method that generates a list of files to send to platform
					cst.getFiles().addAll(listOfFile); 
					//Send unit files to platform
					BackendHandler.sendFiles(cst, listOfFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ShelfRuntimeException("Can not instantiate component files", e);
		}
		return null;
	}

		public static boolean deployCallback(String sessionID, String uri) throws ShelfRuntimeException{
			try {
				SessionHandler.setUri(sessionID, uri);
//				Guardar em XML o CST
				
//				deploySoftwareComponent(cst);
	//			platform.setNetworkIpAddress(PlatformHandler.getPlatformIP(platform.getPlatformContract().getCcId()));
				LogHandler.getLogger().severe("Plataforma Instanciada - Sessão "+sessionID+" uri: "+uri);
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				System.out.println("Sessão "+sessionID+" uri: "+uri);
				System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
				return true;
			} catch (Exception e) {
	//			throw new ShelfRuntimeException(platform.getPlatformContract().getCcId()+" > No ip valid ip address", e1);
			}
	
			
			return false;
		}
	public static String instantiateComponent(ComputationalSystemType cst) throws ShelfRuntimeException{
//		Instantiate component
		try{
//			As linhas abaixo estão definidas para o teste específico, deve ser incluído qual o arquivo principal deve ser executado no banco de dados e corrigir as linhas abaixo
			String target = null;
			for(UnitFileType uft : cst.getFiles()){
				if(uft.getFiletype()==3){
					target = uft.getPath().replace('.', '/')+"/"+uft.getFilename()+"."+uft.getExtension();
				}
			}
			return setRunnable(cst.getNetworkAddress(),target);// /tmp/root/Software/mImgTbl/teste.sh
			//Call backend stub to instantiate component 
			//if not possible, throw an exception
		}catch(Exception e){
			throw new ShelfRuntimeException("Can not instantiate component files", e);
		}
	}


	public static boolean finishSession(int sessionID) throws ShelfRuntimeException {
		try {
			return SessionHandler.destroySession(sessionID);
		} catch (DBHandlerException e) {
			throw new ShelfRuntimeException("Can not destroy session", e);
		}
	}
	
	public static String releasePlatform(ComputationalSystemType uri) {
		
		BackEndServicesImplServiceStub service;
		try {
			System.out.println("http://"+uri.getNetworkAddress()+":"+uri.getPort()+"/BackEndServices/");
			service = new BackEndServicesImplServiceStub("http://"+uri.getNetworkAddress()+":"+uri.getPort()+"/BackEndServices/");
			BackEndServicesImplServiceStub.DestroyPlatform request = new DestroyPlatform();
			request.setArg0(uri.getSession()+"");
//			request.setArg0(profile);
//			request.setArg1(session+"");
			BackEndServicesImplServiceStub.DestroyPlatformResponse response = service.destroyPlatform(request);
			finishSession(uri.getSession());
			return response.get_return();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	private static String setRunnable(String target, String command) {

		FakeEndServicesStub stub = null;
		try {
			stub = new FakeEndServicesStub("http://"+target+":8080/axis2/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");
			//stub = new FakeEndServicesStub("http://localhost:8080/HPC-Shelf-FakeEnd/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
		//Cria a requisicao para o servico
		FakeEndServicesStub.SetRunnable request;
		request = new FakeEndServicesStub.SetRunnable();
		request.setPath(command);
		SetRunnableResponse response = null;
		try {
			response = stub.setRunnable(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return response.get_return();
	}
	
	private static String run(String target, String command) {

		FakeEndServicesStub stub = null;
		try {
			stub = new FakeEndServicesStub("http://"+target+":8080/axis2/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");
			//stub = new FakeEndServicesStub("http://localhost:8080/HPC-Shelf-FakeEnd/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
		//Cria a requisicao para o servico
		FakeEndServicesStub.RunFile request;
		request = new FakeEndServicesStub.RunFile();
		request.setPath(command);
		RunFileResponse response = null;
		try {
			response = stub.runFile(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return response.get_return();
	}

	public static String getStatus(String target) {

		FakeEndServicesStub stub = null;
		try {
			stub = new FakeEndServicesStub("http://"+target+":8080/axis2/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");//
			//			stub = new FakeEndServicesStub("http://localhost:8080/HPC-Shelf-FakeEnd/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
		//Cria a requisicao para o servico
		FakeEndServicesStub.GetStatus request;
		request = new FakeEndServicesStub.GetStatus();
		GetStatusResponse response = null;
		try {
			response = stub.getStatus(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return response.get_return();
	}

	public static String requestPlatform(String target, int session, int port, String profile) {
		BackEndServicesImplServiceStub service;
		try {
			System.out.println("http://"+target+":"+port+"/BackEndServices/");
			service = new BackEndServicesImplServiceStub("http://"+target+":"+port+"/BackEndServices/");
			BackEndServicesImplServiceStub.DeployContractCallback request = new DeployContractCallback();
			request.setArg0(profile);
			request.setArg1(session+"");
			BackEndServicesImplServiceStub.DeployContractCallbackResponse response = service.deployContractCallback(request);
			return response.get_return();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
		
//		
//		BackEndServiceStub stub = null;
//		try {
//			stub = new BackEndServiceStub(target);//
//			System.out.println(stub.toString());
//		} catch (AxisFault e1) {
//			e1.printStackTrace();
//		}
//		Deploy_contract_callback request;
//		request = new Deploy_contract_callback();
//		request.setCore_session_id(session+"");
//		request.setProfile_id(profile);
//		Deploy_contract_callback_result response = null;
//		try {
//			response = stub.deploy_contract_callback(request);
//		} catch (RemoteException | Error e) {
//			e.printStackTrace();
//		}
//		return response.getResult();
	}

//	public static String requestPlatformAxis(String target, int session, int port, String profile) {
//		BackEndServiceStub stub = null;
//		try {
//			stub = new BackEndServiceStub("http://"+target+":"+port+"/backendservices/");
//			System.out.println(stub.toString());
//		} catch (AxisFault e1) {
//			e1.printStackTrace();
//		}
//		Deploy_contract_callback request;
//		request = new Deploy_contract_callback();
//		request.setCore_session_id(session+"");
//		request.setProfile_id(profile);
//		Deploy_contract_callback_result response = null;
//		try {
//			response = stub.deploy_contract_callback(request);
//		} catch (RemoteException | Error e) {
//			e.printStackTrace();
//		} catch (br.ufc.mdcc.www.hpcshelf.backend.Error e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return response.getResult();
//	}
	
	//Validate this method
	private static void sendFiles(ComputationalSystemType cst, List<UnitFileType> listOfFile) {
		String target = "http://"+cst.getNetworkAddress()+":8080/HPC-Shelf-FakeEnd/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/";
		for(UnitFileType uft : listOfFile){
			try {
				sendFile( PropertiesHandler.getProperty("core.library.path")+"/"+uft.getPath().replace('.', '/')+"/"+uft.getFilename()+"."+uft.getExtension(), cst.getNetworkAddress(), uft.getPath().replace('.', '/')+"/"+uft.getFilename()+"."+uft.getExtension());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private static boolean sendFile(String sourcePath, String target, String toPath) {

		FakeEndServicesStub stub = null;
		try {
			//stub = new FakeEndServicesStub();
			stub = new FakeEndServicesStub("http://"+target+":8080/axis2/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
		//Cria a requisicao para o servico
		FakeEndServicesStub.AddFile request;
		request = new FakeEndServicesStub.AddFile();
		request.setFullPath(toPath);
		javax.activation.FileDataSource fileDataSource = new javax.activation.FileDataSource(sourcePath);
		javax.activation.DataHandler dh = new javax.activation.DataHandler(fileDataSource);

		request.setFile(dh);
		AddFileResponse response = null;
		try {
			response = stub.addFile(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return response.get_return();
	}
	
	private static String deleteFile(String target, String toPath) {

		FakeEndServicesStub stub = null;
		try {
			stub = new FakeEndServicesStub("http://"+target+":8080/axis2/services/FakeEndServices.FakeEndServicesHttpSoap12Endpoint/");
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
		//Cria a requisicao para o servico
		FakeEndServicesStub.RemoveFile request;
		request = new FakeEndServicesStub.RemoveFile();
		request.setPath(toPath);
		RemoveFileResponse response = null;
		try {
			response = stub.removeFile(request);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return response.get_return();
	}
	
	private static String readFile(String pathname) throws IOException {

		File file = new File(pathname);
		StringBuilder fileContents = new StringBuilder((int)file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");
		try {
			while(scanner.hasNextLine()) {        
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}

}
